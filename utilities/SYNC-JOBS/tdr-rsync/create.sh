oc -n ers-prod create cm rsync-ssh-cm-id --from-file=ssh/id_rsa
oc -n ers-prod create cm rsync-ssh-cm-idpub --from-file=ssh/id_rsa.pub
oc -n ers-prod create cm rsync-ssh-cm-kh --from-file=ssh/known_hosts
oc -n ers-prod apply -f cronjob.yaml 
