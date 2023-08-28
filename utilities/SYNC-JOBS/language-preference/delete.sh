oc -n ers-prod delete -f cronjob.yaml
oc -n ers-prod delete configmap lang-pref-daily-script 
oc -n ers-prod delete configmap lang-pref-weekly-script 
oc -n ers-prod delete configmap lang-pref-ssh-known-host 
oc -n ers-prod delete configmap lang-pref-ssh-config 
oc -n ers-prod delete secret  lang-pref-sftp-pass
