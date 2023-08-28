
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap bss-config --from-file=config --namespace ers-prod
oc create configmap bss-datafeeder-template --from-file=config/datafeeder/templates --namespace ers-prod
oc create configmap bss-samplecsv --from-file=config/samplecsv --namespace ers-prod
oc create configmap bss-config-templates --from-file=templates --namespace ers-prod
oc create configmap copy-bss-configs --from-file=scripts --namespace ers-prod
oc create configmap bss-notifications --from-file=notifications --namespace ers-prod
