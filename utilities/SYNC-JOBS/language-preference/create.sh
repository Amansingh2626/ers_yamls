oc -n ers-prod create secret generic lang-pref-sftp-pass --from-literal=sftp-pass=oK70bTrXCtHQw
oc -n ers-prod create configmap lang-pref-daily-script --from-file=scripts/notification-language-daily.sh
oc -n ers-prod create configmap lang-pref-weekly-script --from-file=scripts/notification-language-weekly.sh
oc -n ers-prod create configmap lang-pref-ssh-known-host --from-file=scripts/known_hosts
oc -n ers-prod create configmap lang-pref-ssh-config --from-file=scripts/config
oc -n ers-prod create -f cronjob.yaml
