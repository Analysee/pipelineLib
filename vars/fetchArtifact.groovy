def call(String repository,String artifactName,String version, String groupId){
sh "curl -o target/ROOT.war  http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/${repository}/${groupId}/${artifactName}/${version}/${artifactName}-${version}.war"
}