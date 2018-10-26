def call (String message){
timeout(time:5, unit:'DAYS') {
    input message:message
}