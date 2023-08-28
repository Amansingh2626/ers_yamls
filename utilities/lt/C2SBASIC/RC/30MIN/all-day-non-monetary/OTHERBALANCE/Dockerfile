FROM default-route-openshift-image-registry.apps.pocpbm-nexp.grameenphone.com/openshift/locust:v1 
RUN  mkdir -p /home/locust/scripts 
RUN  mkdir -p /home/locust/data
COPY locust.py /home/locust/scripts/.
COPY run.sh /home/locust/scripts/.
COPY gp_otherbalance_data.csv /home/locust/data/.
COPY gp_otherbalance_body.xml /home/locust/data/.
ENV  SERVER_FQDN "https://ers.grameenphone.com"
ENV  USER_COUNT 1
ENV  SPAWN_RATE 1
ENV  EXTRA_PARAMS ""
