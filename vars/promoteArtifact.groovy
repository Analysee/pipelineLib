def call(String artifactName,String version,String stagingRepository2,String releaseRepository2){
	        artifactPromotion (
        promoterClass:'org.jenkinsci.plugins.artifactpromotion.NexusOSSPromotor',
        groupId: artifactName,
        artifactId: artifactName,
        version:version,
        extension: 'war',
        stagingRepository: 'http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/lindacaretest',
        stagingUser: 'admin',
        stagingPW: 'admin123',
        skipDeletion: true,
        releaseRepository: 'http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/lindacareint',
        releaseUser: 'admin',
        releasePW: 'admin123'
    ) 
}