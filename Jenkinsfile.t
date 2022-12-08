pipeline {
  agent any
  stages {
    stage('打包发布') {
      steps {
        sh 'mvn clean deploy  sonar:sonar -Dmaven.test.skip=true -Dgpg.passphrase=${gpg_password}'
      }
    }
  }
}