pipeline {
    agent any
    stages {
        stage('Jenkins Account') {
            steps {
                echo 'Account Service'
            }
        }

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
    }
}