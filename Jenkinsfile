pipeline {
    agent any

    environment {
        // Définition de l'outil Maven
        MAVEN_HOME = tool 'maven-3.9.6'
        // Ajout du chemin vers Maven dans le PATH
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        SCANNER_HOME = tool 'Sonarqube'
        
    }

    stages {
        stage('Vérification GitHub') {
            steps {
                // Étape pour récupérer le code source depuis Git
                git url: 'https://github.com/Ndaw11/untilied.git', branch: 'main'
            }
        }

        stage('Build de Maven') {
            steps {
                // Étape pour construire le projet avec Maven
                sh 'mvn clean install'
            }
        }
         stage('Analyse SonarQube') {
            steps {
                script {
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=untilied -Dsonar.projectName='untilied' -Dsonar.host.url=http://192.168.1.8:9000 -Dsonar.token=sqp_5408f76c6604eb0643bf7de01e5e8054b12ae1f7"
                }
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
