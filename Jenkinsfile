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
            sh '''
                 chmod 400 "recipe_key_pair.pem"
                 rsync -avz --exclude '.git' --exclude 'target' --exclude 'recipe_key_pair.pem' -e "ssh -i recipe_key_pair.pem" . ubuntu@ec2-3-84-242-75.compute-1.amazonaws.com:~/app
                 ssh -i "recipe_key_pair.pem" ubuntu@ec2-3-84-242-75.compute-1.amazonaws.com "
                  pkill java || true
                 cd ~/app
                 mvn clean package -DskipTests
                 nohup java -jar target/recipe-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > app.log 2>&1 &
                 "
            '''
        }
       }
    }
}