def call(String repository,String artifactName,String version){
sh 'curl -o fetchedArtifact.war  http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/${artifactName}/com/rutvikpatel/springboot/helloworld/${artifactName}/${version}/${artifactName}-${version}.war'
}