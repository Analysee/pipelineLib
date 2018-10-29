def call(String appName){        
	withCredentials([azureServicePrincipal('mySP2')]) {
        sh 'az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID'
        sh 'az account set -s $AZURE_SUBSCRIPTION_ID'
		azureWebAppPublish azureCredentialsId: 'mySP2',
        resourceGroup: 'lindacare-jenkins-test', appName: appName,
        filePath: 'fetchedArtifact.war', sourceDirectory: 'target', targetDirectory: 'webapps'
		sh 'az logout'
	}
}