// vars/functionAppPipeline.groovy
def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()


	// build specific params
    def VERSION = "${config.VERSION}.${BUILD_NUMBER}"
	def ARTIFACT_NAME = "${config.BUILD_NAME}-${BRANCH_NAME}-${VERSION}"
	def TESTFILE = "results-${VERSION}"

	// dump out what we are doing
	echo "build version: ${VERSION}"
	echo "Setting build name to: ${ARTIFACT_NAME}"

	// set the build name and description
	currentBuild.displayName = ARTIFACT_NAME
	currentBuild.description = "CI / CD pipeline for ${ARTIFACT_NAME}"

	// setup the trigger for bitbucket, the polling makes the auto build on commit work
    properties([
    	disableConcurrentBuilds(),
    	pipelineTriggers([
    		
    		pollSCM('H H * * *')
    	])
    ])   
    node {	
		// set the workspace manually because some paths can get to long
        ws("${workspace}") {
		 	try{
			stage('build'){
			task('checkout'){
			checkout scm
			}
			task('maven build'){
			withMaven(maven: 'maven'){
			sh 'mvn clean package'
			sh 'mv target/*.war target/ROOT.war'
			}
			}
			//task('sonarqube analysis'){
			//sonarQubeAnalysis()
			//}
			task('upload artifact'){
			nexusArtifactUpload(VERSION,ARTIFACT_NAME,'lindacaretest')
			}
			}
			stage('dev'){
			task('fetch artifact from lindacaretest'){
			fetchArtifact('lindacaretest',ARTIFACT_NAME,VERSION)
			}
			task('azure test deployment approval'){
			approve('Deploy to azure test environment?')
			}
			task('azure test deployment'){
			azureDeploy('lindacare-test');
			}
			task('promote artifact test to int approval'){
			approve('Promote artifact test to int?')
			}
			task('promote artifact test to int'){
			promoteArtifact(ARTIFACT_NAME,VERSION,'lindacaretest','lindacareint','testEnvironment')
			}
			task('fetch artifact from lindacareint'){
			fetchArtifact('lindacareint',ARTIFACT_NAME,VERSION)
			}
			task('azure int deployment approval'){
			approve('Deploy to azure int environment?')
			}
			task('azure int deployment'){
			azureDeploy('lindacare-int');
			}
			task('promote artifact int to uat approval'){
			approve('Promote artifact int to uat?')
			}
			task('promote artifact int to uat'){
			promoteArtifact(ARTIFACT_NAME,VERSION,'lindacareint','lindacareuat','testEnvironment')
			}
			task('fetch artifact from lindacareuat'){
			fetchArtifact('lindacareuat',ARTIFACT_NAME,VERSION)
			}
			task('azure int deployment approval'){
			approve('Deploy to azure int environment?')
			}
			task('azure int deployment'){
			azureDeploy('lindacare-uat');
			}
			task('promote artifact uat to prod approval'){
			approve('Promote artifact uat to prod?')
			}
			task('upload artifact'){
			nexusArtifactUpload(VERSION,ARTIFACT_NAME,'lindacareprod')
			}
			task('promote artifact int to uat'){
			promoteArtifact(ARTIFACT_NAME,VERSION,'lindacareuat','lindacareprod','testEnvironment')
			}
			}
			} catch (err) {
				echo "BUILD FAILED"
		        echo "Caught: ${err}"
		        currentBuild.result = 'FAILURE'
    		} finally {
    			echo "Cleaning up workspace"
                deleteDir()
            }
        }
    }
}