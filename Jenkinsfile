pipeline {
    agent any
    stages {
        stage('Build Interface') {
            steps {
                build job: 'Account', wait: true
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Image') {
            steps {
                script {
                    account = docker.build("pejassinaturasdocker/account:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }
        stage('Push Image'){
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credential') {
                        account.push("${env.BUILD_ID}")
                        account.push("latest")
                    }
                }
            }
        }
        stage('Deploy on k8s') {
            steps {
                withCredentials([ string(credentialsId: 'minikube-credential', variable: 'api_token') ]) {
                    sh 'kubectl --token $api_token --server https://172.17.0.1:32769 --insecure-skip-tls-verify=true apply -f ./k8s/deployment.yaml'
                    sh 'kubectl --token $api_token --server https://172.17.0.1:32769 --insecure-skip-tls-verify=true apply -f ./k8s/service.yaml'
                }
            }
        }
    }
}