

function process(r){
        var queryString = r.variables.query_string;
	var authEndpoint = '/auth'
        if(r.headersIn['changeEndpoint']=="true" ){
            authEndpoint = '/target'
        }
	    r.log('authentication endpoint '+authEndpoint);
        r.subrequest(authEndpoint,{'args':queryString}).
        then(res => {
                if(res.status != 200){
                   r.return(res.status,res.responseBody);
                }else{
                        var uri = r.uri;
                        uri = uri.substring(getPosition(uri,'/',2));
                        r.variables.systemToken = res.headersOut['system-token'];
                        r.variables.idmsAuthToken = res.headersOut['authorization'];
                        r.variables.receiverId = res.headersOut['receiverId'];
                        r.variables.receiverMsisdn = res.headersOut['receiverMsisdn'];
                        var isTxeProxyCall = res.headersOut['isTxeProxyCall'];
			            var contentTypeHeader = res.headersIn['Content-Type'];
                        if(isTxeProxyCall=="true")
                        {
                        var isNestedRequestBody = res.headersOut['isNestedRequest'];
                        if(isNestedRequestBody=="true" && contentTypeHeader.includes('xml')){
                           uri="/txe-proxy/v2/gp/endPoint"
                        } else if(isNestedRequestBody == undefined){
                            uri="/txe-proxy/gp/endPoint"
                        }
                        }
                        r.log("Resolved URI is : "+uri+" is xml content type "+contentTypeHeader.includes('xml'))
                        if(queryString){
                           uri +='?'+queryString;
                        }
                        r.internalRedirect('/backend'+uri);
                }
        });
}

function getPosition(string, subString, index) {
  return string.split(subString, index).join(subString).length;
}

function validateOtpAndResetPassword(r){

        r.variables.originalURI = 'standard-link/otp/resetPassword';
        r.variables.rootComp = 'standard-link';
        r.subrequest('/otpVerifyToken',{'method':'POST'}).
        then(res => {
            if(res.status != 200){
                r.return(res.status,res.responseBody);
            }else{
                r.variables.systemToken = res.headersOut['system-token'];
                r.variables.idmsAuthToken = res.headersOut['authorization'];
                r.subrequest('/login-backend',{'args':'validateOnly=true','method':'POST'}).
                then(res => {
                    if(res.status != 200){
                         r.return(res.status,res.responseBody);
                    }else{
                        r.internalRedirect('/backend/standard-link/request');
                    }
                })
            }
        });
}

export default {process,validateOtpAndResetPassword};
