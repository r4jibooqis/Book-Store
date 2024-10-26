pipeline {
    agent any 

    environment {
        DOCKER_IMAGE = 'book_store:latest'
        DOCKER_CONTAINER_NAME = 'book_store_local'
        LOCAL_PORT = '8080' // Local port to access the application
    }

    stages {
        stage('Source Code Checkout') {
            steps {
                git url: "https://github.com/r4jibooqis/Book-Store.git", credentialsId: "${GIT_CREDENTIALS_ID}"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                script {
                    def testResults = sh(script: 'mvn test', returnStatus: true)
                    if (testResults != 0) {
                        error("JUnit tests failed.")
                    }
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Deploy Locally with Docker') {
            when {
                expression { currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                script {
                    // Stop and remove any existing container for the app
                    sh "docker rm -f ${DOCKER_CONTAINER_NAME} || true"
                    // Run the Docker container locally
                    sh "docker run -d --name ${DOCKER_CONTAINER_NAME} -p ${LOCAL_PORT}:8080 ${DOCKER_IMAGE}"
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
        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
}