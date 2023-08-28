find . -name '01*.yaml' | xargs -n1 -i oc -n ers-prod delete -f  {}
