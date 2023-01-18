pipeline {
    agent {
        label 'new-node'
    }
    stages {
        stage('git-pull') {
            steps { 
                echo 'git pull successful'
                sh 'echo $env'
            }
        }
        stage('build') {
            steps { 
                echo 'build successful'
                sh 'ls'
            }
        }
        stage('testing') {
            steps { 
                echo 'testing successful'
                sh 'pwd'
            }
        }
        stage('deploying') {
            steps { 
                echo 'deploy completed'
            }
        }
    }
}
