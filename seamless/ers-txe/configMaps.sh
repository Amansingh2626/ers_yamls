
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



kubectl create configmap txe-config --from-file=config --namespace ers-prod
kubectl create configmap txe-config-templates --from-file=templates --namespace ers-prod
kubectl create configmap txe-config-rules --from-file=rules --namespace ers-prod
kubectl create configmap txe-config-actions --from-file=actions --namespace ers-prod
kubectl create configmap txe-config-pricing --from-file=pricing --namespace ers-prod
kubectl create configmap txe-config-samples --from-file=samples/minimum --namespace ers-prod
kubectl create configmap txe-config-mysql --from-file=mysql --namespace ers-prod
kubectl create configmap copy-script --from-file=scripts --namespace ers-prod
kubectl create -f filebeat/filebeat-configmap.yaml
