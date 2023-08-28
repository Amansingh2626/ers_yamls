locust -f /home/locust/scripts/locust.py -H ${SERVER_FQDN} -u ${USER_COUNT} -s ${SPAWN_RATE} ${EXTRA_PARAMS} --headless
