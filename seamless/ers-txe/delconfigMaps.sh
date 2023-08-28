kubectl delete configmap txe-config  --namespace ers-prod
kubectl delete configmap txe-config-templates --namespace ers-prod
kubectl delete configmap txe-config-rules  --namespace ers-prod
kubectl delete configmap txe-config-actions  --namespace ers-prod
kubectl delete configmap txe-config-pricing  --namespace ers-prod
kubectl delete configmap txe-config-samples  --namespace ers-prod
kubectl delete configmap txe-config-mysql    --namespace ers-prod
kubectl delete configmap copy-script --namespace ers-prod
kubectl delete -f filebeat/filebeat-configmap.yaml
