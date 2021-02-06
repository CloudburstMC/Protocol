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
            stages {
                stage ('Compile') {
                    when { not { anyOf {
                        branch 'master'
                        branch 'develop'
                    }}}

                    steps {
                        sh 'mvn clean package'
                    }
                }
                stage ('Deploy') {
                    when { anyOf {
                        branch 'master'
                        branch 'develop'
                    }}

                    stages {
                        stage ('Setup') {
                            steps {
                                rtMavenDeployer (
                                        id: "maven-deployer",
                                        serverId: "opencollab-artifactory",
                                        releaseRepo: "maven-releases",
                                        snapshotRepo: "maven-snapshots"
                                )
                                rtMavenResolver (
                                        id: "maven-resolver",
                                        serverId: "opencollab-artifactory",
                                        releaseRepo: "release",
                                        snapshotRepo: "snapshot"
                                )
                            }
                        }

                        stage ('Release') {
                            when {
                                branch 'master'
                            }

                            steps {
                                rtMavenRun (
                                        pom: 'pom.xml',
                                        goals: 'javadoc:jar source:jar install',
                                        deployerId: "maven-deployer",
                                        resolverId: "maven-resolver"
                                )
                                sh 'mvn javadoc:aggregate -pl bedrock/bedrock-common -am -DskipTests'
                                step([$class: 'JavadocArchiver', javadocDir: 'target/site/apidocs', keepAll: false])
                            }
                        }

                        stage ('Snapshot') {
                            when {
                                branch 'develop'
                            }
                            steps {
                                rtMavenRun (
                                        pom: 'pom.xml',
                                        goals: 'javadoc:jar source:jar install',
                                        deployerId: "maven-deployer",
                                        resolverId: "maven-resolver"
                                )
                            }
                        }

                        stage ('Publish') {
                            steps {
                                rtPublishBuildInfo (
                                        serverId: "opencollab-artifactory"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}