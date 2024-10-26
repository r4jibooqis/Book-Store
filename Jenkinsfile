pipeline {
    agent any 

    environment {
        DOCKER_IMAGE = 'book_store:latest'
        DOCKER_CONTAINER_NAME = 'book_store_local'
        LOCAL_PORT = '8080' // Local port to access the application
        MAVEN_HOME = 'C:\\Program Files\\Maven\\apache-maven-3.9.9' // Change to your Maven installation path
        PATH = "${MAVEN_HOME}/bin:${env.PATH}" // Update the PATH variable
    }

    stages {
        stage('Source Code Checkout') {
            steps {
                git url: "https://github.com/r4jibooqis/Book-Store.git", credentialsId: "git-credentials"
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                script {
                    def testResults = bat(script: 'mvn test', returnStatus: true)
                    if (testResults != 0) {
                        error("JUnit tests failed.")
                    }
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }

        stage('Deploy Locally with Docker') {
            when {
                expression { currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                script {
                    // Stop and remove any existing container for the app
                    bat "docker rm -f ${DOCKER_CONTAINER_NAME} || exit 0"
                    // Run the Docker container locally
                    bat "docker run -d --name ${DOCKER_CONTAINER_NAME} -p ${LOCAL_PORT}:8080 ${DOCKER_IMAGE}"
                }
            }
        }
    }

    post {
        success {
            script {
                sendEmailNotification("Local deployment successful!", "SUCCESS")
            }
        }
        failure {
            script {
                sendEmailNotification("Local deployment failed!", "FAILURE")
            }
        }
    }
}

def sendEmailNotification(String subject, String body) {
    emailext(
        subject: subject,
        body: body,
        to: 'raji.makkah@gmail.com'
    )
}
