def azureTestDeloymentApproval(){
timeout(time:5, unit:'DAYS') {
    input message:'Approve deployment to azure test environment?'
}