
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap object-store-config --from-file=config --namespace ers-prod
oc create configmap object-store-config-locales --from-file=locales --namespace ers-prod
