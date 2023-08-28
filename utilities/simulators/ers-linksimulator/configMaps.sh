
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



kubectl create configmap  linksimulator-config --from-file=config --namespace ers-prod

