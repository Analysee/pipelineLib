def call(String version, String artifactName){
     nexusPublisher nexusInstanceId: 'Nexus1', nexusRepositoryId: 'lindacaretest', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'target/ROOT.war']], mavenCoordinate: [artifactId: artifactName, groupId: 'testEnvironment', packaging: 'war',version:version]]]
}