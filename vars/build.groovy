def build(){
if (isUnix()) {
	mvnHome = tool 'maven'
    sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
    } else {
    bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
    }
    sh 'mv target/*.war target/ROOT.war'
}