def call(String artifactName,String version,String stagingRepository2,String releaseRepository2,String environment){
	        artifactPromotion (
        promoterClass:'org.jenkinsci.plugins.artifactpromotion.NexusOSSPromotor',
        groupId: 'testEnvironment',
        artifactId: artifactName,
        version:version,
        extension: 'war',
        stagingRepository: 'http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/'+stagingRepository2,
        stagingUser: 'admin',
        stagingPW: 'admin123',
        skipDeletion: true,
        releaseRepository: 'http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/'+releaseRepository2 +'/',
        releaseUser: 'admin',
        releasePW: 'admin123'
    ) 
}