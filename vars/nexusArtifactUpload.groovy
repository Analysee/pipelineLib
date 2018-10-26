def call(String version, String artifactName,Sting nexusRepositoryId){
     nexusPublisher nexusInstanceId: 'Nexus1', nexusRepositoryId: nexusRepositoryId, packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'target/ROOT.war']], mavenCoordinate: [artifactId: artifactName, groupId: 'testEnvironment', packaging: 'war',version:version]]]
}