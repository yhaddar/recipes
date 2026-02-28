pipeline {
    agent any
    environment {
        SONARQUBE = 'SonarQube'
    }
    stages {
       stage('Build') {
            agent any
            tools {
                maven 'maven3'
                jdk 'jdk25'
            }
            steps {
                echo "Building in progress..."
                sh '''
                    mvn clean package -DskipTests
                '''
            }
       }
       stage('Analyse') {
            tools {
                maven 'maven3'
            }
            steps {
                echo 'start analysing...'
                withSonarQubeEnv('SonarQube'){
                    sh 'mvn sonar:sonar -Dsonar.java.binaries=target/classes'
                }
            }
       }
       stage("Test"){
            tools {
               maven 'maven3'
               jdk 'jdk25'
            }
            steps {
                echo "Unit Testing in progress..."
                sh '''
                    mvn test
                '''
            }
       }
       stage("Deploy"){
        steps {
            echo "start deployement in EC2..."
            withCredentials([sshUserPrivateKey(credentialsId: 'ec2-key', keyFileVariable: 'SSH_KEY')]){
                sh '''
                  chmod 400 ${SSH_KEY}
                 rsync -avz --exclude '.git' --exclude 'target' -e "ssh -i ${SSH_KEY}" . ubuntu@ec2-3-84-242-75.compute-1.amazonaws.com:~/app
                 ssh ubuntu@ec2-3-84-242-75.compute-1.amazonaws.com "
                 sudo systemctl restart recipeapp.service
                 "
            '''
            }
        }
       }
    }
}