for deployment in $( cat /data/deployment.txt )
do
oc rollout restart deployment  ${deployment} -n ers-prod && oc rollout status deployment ${deployment} -n ers-prod
done
