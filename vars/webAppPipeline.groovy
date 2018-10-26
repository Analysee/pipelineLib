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
    		[
    			$class: 'GitHubTrigger'
    		],
    		pollSCM('H H * * *')
    	])
    ])   
    node {	
		// set the workspace manually because some paths can get to long
        ws("${workspace}") {
		 	try{
			stage("preparation){
				git 'https://github.com/Analysee/jenkins2.git' 
			}
			stage("build"){
				 withMaven(maven: 'maven'){
				sh 'mvn clean package'
				sh 'mv target/*.war target/ROOT.war'
			}
			}
			stage("sonarQubeAnalysis"){
			SonarQubeAnalysis()
			}
			stage('Upload to nexus'){
			nexusArtifactUpload(VERSION,ARTIFACT_NAME)
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