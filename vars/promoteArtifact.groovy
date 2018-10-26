def call(String artifactName,String version,String stagingRepository,String releaseRepository){
	        artifactPromotion (
        promoterClass:'org.jenkinsci.plugins.artifactpromotion.NexusOSSPromotor',
        groupId: 'com.rutvikpatel.springboot.helloworld',
        artifactId: artifactName,
        version:version
        extension: 'war',
        stagingRepository: 'http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/${stagingRepository}',
        stagingUser: 'admin',
        stagingPW: 'admin123',
        skipDeletion: true,
        releaseRepository: 'http://jenkins5lindacare.westeurope.cloudapp.azure.com:8081/repository/${releaseRepository}',
        releaseUser: 'admin',
        releasePW: 'admin123'
    ) 