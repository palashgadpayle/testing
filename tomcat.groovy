pipeline {
    agent any
    stages {
        stage('git-pull') {
            steps { 
                sh 'sudo apt-get update -y'
                sh 'sudo apt-get install git -y'
                git credentialsId: 'one', url: 'git@github.com:nishantindorkar/student-ui.git'
                sh 'ls'
            }
        }
        stage('build-maven') {
            steps { 
                sh 'sudo apt-get update -y'
                sh 'sudo apt-get install maven curl unzip -y'
                sh 'mvn clean package'
            }
        }
        stage('build-artifacts') {
            steps { 
                // sh 'curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
                // sh 'unzip awscliv2.zip'
                // sh 'sudo ./aws/install'
                sh 'aws s3 cp **/*.war s3://studentngpbckt/student-${BUILD_ID}.war'
            }
        }
        stage('tomcat-build') {
            steps { 
                withCredentials([sshUserPrivateKey(), sshUserPrivateKey(credentialsId: 'cat', keyFileVariable: 'tomcat')]) 
                {
                sh '''
                ssh -i ${tomcat} -o StrictHostKeyChecking=no ubuntu@3.84.10.201<<EOF
                #sh 'curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
                #sh 'unzip awscliv2.zip'
                #sh 'sudo ./aws/install'
                #aws s3 cp s3://studentngpbckt/student-${BUILD_ID}.war .
                #sudo apt-get update -y
                #sudo apt install openjdk-11-jdk -y
                #curl -O https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.85/bin/apache-tomcat-8.5.85.tar.gz'
                #sudo tar -xvf apache-tomcat-8.5.85.tar.gz -C /opt/
                #sudo sh /opt/apache-tomcat-8.5.85/bin/shutdown.sh
                #sudo cp -rv student-${BUILD_ID}.war studentapp.war
                #sudo cp -rv studentapp.war /opt/apache-tomcat-8.5.85/webapps/
                #sudo ./opt/apache-tomcat-8.5.85/bin/startup.sh
                '''
                }
            }
        }
    }
}