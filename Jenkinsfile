
pipeline {
    agent any
    tools {
        maven 'maven-3.9.6'
       
    }
    stages {
        stage('Verification github') {
            steps {
                // Étape pour récupérer le code source depuis Git
                git url: 'github.com/Ndaw11/untilied.git', branch: 'main'
            }
        }
        
        stage('Build de Maven') {
            steps {
                // Étape pour construire le projet avec Maven
                sh 'mvn clean install'
            }
        }
       
    }
    
    post {
        success {
            // Actions à effectuer en cas de succès
            echo 'Le projet a été récupéré, construit et analysé avec succès!'
        }
        failure {
            // Actions à effectuer en cas d'échec
            echo 'La récupération, la construction ou l\'analyse du projet a échoué.'
        }
    }
}
