pipeline {
    agent any
    tools {
        maven 'Maven 3'
        jdk 'Java 8'
    }
    options {
        buildDiscarder(logRotator(artifactNumToKeepStr: '1'))
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Snapshot') {
            when {
                branch "develop"
            }
            steps {
                sh 'mvn source:jar deploy -DskipTests'
            }
        }

        stage ('Release') {
            when {
                branch "master"
            }
            steps {
                sh 'mvn javadoc:aggregate -pl bedrock/bedrock-common -am -DskipTests'
                sh 'mvn javadoc:jar source:jar deploy -DskipTests'
            }
            post {
                success {
                    step([
                        $class: 'JavadocArchiver',
                        javadocDir: 'target/site/apidocs',
                        keepAll: false
                    ])
                }
            }
        }
    }
}