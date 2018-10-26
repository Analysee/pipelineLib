def nexusArtifactUpload(String version, String artifactName){
 pom = readMavenPom file: 'pom.xml'
     nexusPublisher nexusInstanceId: 'Nexus1', nexusRepositoryId: 'azuremaven', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'target/ROOT.war']], mavenCoordinate: [artifactId: artifactName, groupId: 'testEnvironment', packaging: 'war',version:version]]]
}