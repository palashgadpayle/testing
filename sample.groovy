pipeline {
    agent any
    stages {
        stage('git-pull') {
            steps { 
                echo 'git pull successful'
            }
        }
        stage('build') {
            steps { 
                echo 'build successful'
            }
        }
        stage('testing') {
            steps { 
                echo 'testing successful'
            }
        }
        stage('deploying') {
            steps { 
                echo 'deploy completed'
            }
        }
    }
}
