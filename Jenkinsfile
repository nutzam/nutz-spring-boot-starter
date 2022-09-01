pipeline {
  agent any
  stages {
    stage('SonarQube') {
      steps {
        sh 'mvn clean package  sonar:sonar'
      }
    }
    stage('打包发布') {
      steps {
        sh 'mvn clean deploy -Dmaven.test.skip=true -Dgpg.passphrase=742425'
      }
    }
  }
}
