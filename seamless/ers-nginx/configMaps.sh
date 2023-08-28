
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}


sed -i  "s+access\.log+access-one\.log+g"        nginx.conf
oc create configmap nginx-conf-one   --from-file=nginx.conf --namespace ers-prod
sed -i  "s+access-one\.log+access-two\.log+g"    nginx.conf
oc create configmap nginx-conf-two   --from-file=nginx.conf --namespace ers-prod
sed -i  "s+access-two\.log+access-three\.log+g"  nginx.conf
oc create configmap nginx-conf-three   --from-file=nginx.conf --namespace ers-prod
oc create configmap nginx-config --from-file=conf.d --namespace ers-prod
oc create configmap nginx-config-script --from-file=conf.d/script --namespace ers-prod
oc create configmap nginx-config-support --from-file=support --namespace ers-prod
oc create configmap nginx-config-services --from-file=services --namespace ers-prod
oc create configmap nginx-config-services-public --from-file=services/public --namespace ers-prod
oc create configmap nginx-config-ext --from-file=ext --namespace ers-prod
oc create configmap nginx-config-init --from-file=init --namespace ers-prod
#oc create configmap nginx-config-thirdparty --from-file=thirdParty --namespace ers-prod
#oc create configmap nginx-config-html --from-file=html --namespace ers-prod
#oc create configmap copy-nginx-configs --from-file=scripts --namespace ers-prod
sed -i  "s+access-three\.log+access\.log+g"      nginx.conf
