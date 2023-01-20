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
                //You can now run: /usr/local/bin/aws --version
                sh 'sudo mv /var/lib/jenkins/workspace/student_app/target/studentapp-2.2-SNAPSHOT.war /home/ubuntu/student-${BUILD_ID}.war'
                sh 'aws s3 cp /home/ubuntu/student-${BUILD_ID}.war s3://myawwbucket'
            }
        }
        // stage('tomcat-build') {
        //     steps { 
        //         sh 'wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.85/bin/apache-tomcat-8.5.85.tar.gz'
        //     }
        // }
        // stage('deploying') {
        //     steps { 
        //         echo 'deploy completed'
        //     }
        //}
    }
}




// pipeline{
//  agent any 
//  stages{
//  stage("git-pull"){
//  steps{
//  sh 'sudo apt update -y'
//  sh 'sudo apt install git -y'
//  git credentialsId: 'git', url: 'git@github.com:nishantindorkar/student-ui.git'
//  sh 'ls'
//  }
//  }
//  stage("Building-maven"){
//  steps{
//  sh 'sudo apt-get update -y'
//  sh 'sudo apt-get install maven curl unzip -y'
//  sh 'mvn clean package'
//  }
//  }
//  stage("push-artifact"){
//  steps{
//  sh 'curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
//  sh 'unzip awscliv2.zip'
//  sh 'sudo ./aws/install'
//  sh 'sudo mv /var/lib/jenkins/workspace/student_app/target/studentapp-2.2-SNAPSHOT.war /home/ubuntu/student-${BUILD_ID}.war'
//  sh 'aws s3 cp /home/ubuntu/student-${BUILD_ID}.war s3://new-bucket-artifact'
//  }
//  }
//   stage("Dev-Deployment"){
//   steps{
//      withCredentials([sshUserPrivateKey(credentialsId: 'ssh-keyagent', keyFileVariable: 'tomcat')]) {

//          sh'''
//   ssh -i ${tomcat}  -o StrictHostKeyChecking=no ubuntu@13.229.150.86<<EOF
//     aws s3 cp s3://new-bucket-artifact/student-${BUILD_ID}.war .
//     curl -O https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.82/bin/apache-tomcat-8.5.82.tar.gz
//     sudo tar -xvf apache-tomcat-8.5.82.tar.gz -C /opt/
//     sudo sh /opt/apache-tomcat-8.5.82/bin/shutdown.sh
//     sudo cp -rv student-${BUILD_ID}.war studentapp.war
//     sudo cp -rv studentapp.war /opt/apache-tomcat-8.5.82/webapps/
//     sudo sh /opt/apache-tomcat-8.5.82/bin/startup.sh
//     '''
//       } 
//   }
//   }
//  }
// }
