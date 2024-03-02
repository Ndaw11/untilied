pipeline {
    agent any

    environment {
        // Définition de l'outil Maven
        MAVEN_HOME = tool 'maven-3.9.6'
        // Ajout du chemin vers Maven dans le PATH
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        SCANNER_HOME = tool 'Sonarqube'
        // Cela peut être Nexus3 ou Nexus2
        NEXUS_VERSION = "nexus3"
        // peut être http ou https
        NEXUS_PROTOCOL = "http"
        //Où est exécuté votre Nexus
        NEXUS_URL = "192.168.1.8:8081"
        // Dépôt où nous téléchargerons l'artefact
        NEXUS_REPOSITORY = "untilied1"
        // Identifiant d'identification Jenkins pour s'authentifier auprès de Nexus OSS
        NEXUS_CREDENTIAL_ID = "nexus"
        ARTIFACT_VERSION = "${BUILD_NUMBER}"
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
        stage('Télécharger le projet d\'artefact sur Nexus Repository Manager') {
    steps {
        script {
            // Lisez le fichier XML POM à l'aide de l'étape 'readMavenPom'
            pom = readMavenPom file: "pom.xml";

            // Rechercher l'artefact construit dans le dossier cible
            filesByGlob = findFiles(glob: "target/*.${pom.packaging}");

            // Imprimez quelques informations sur l'artefact trouvé
            echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"

            // Extraire le chemin du fichier trouvé
            artifactPath = filesByGlob[0].path;

            // Attribuer à une réponse booléenne vérifiant si le nom de l'artefact existe
            artifactExists = fileExists artifactPath;

            if (artifactExists) {
                echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";

                nexusArtifactUploader(
                    nexusVersion: NEXUS_VERSION,
                    protocol: NEXUS_PROTOCOL,
                    nexusUrl: NEXUS_URL,
                    groupId: pom.groupId,
                    version: pom.version, // Utiliser pom.version au lieu de ARTIFACT_VERSION
                    repository: NEXUS_REPOSITORY,
                    credentialsId: NEXUS_CREDENTIAL_ID,
                    artifacts: [
                        // Artefact généré tel que les fichiers .jar, .ear et .war.
                        // Mais dans notre cas, on a un .jar
                        [
                            artifactId: pom.artifactId,
                            classifier: '',
                            file: artifactPath,
                            type: pom.packaging
                        ]
                    ]
                );
            } else {
                error "*** File: ${artifactPath}, could not be found";
            }
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
