import time
import requests
import csv
import random
import os
from locust import HttpUser, task, between ,constant,constant_pacing
from locust.user import wait_time

loginDns = os.getenv('SERVER_FQDN') 

def read_user_credentials_from_csv():
    with open("/home/locust/data/gp_exlastxtrfreq_data.csv", 'r') as file:
        lines = [tuple(line) for line in csv.reader(file)] 
    return lines    

csvData = read_user_credentials_from_csv()


class simLastXTxn(HttpUser): 
    
    wait_time = constant_pacing(1)
    @task 
    def simLastXTxn(self):               
        with open('/home/locust/data/gp_exlastxtrfreq_body.xml', 'r') as xml_fh:
            xml_data = xml_fh.read()              
        simLastXTxnHeader = {
                    'content-type': 'application/xml',
                    }
        
        chosen_rows = random.choice(csvData)
        msisdn = chosen_rows[0]
        pin = chosen_rows[1]         
        xml_data  = xml_data.replace("${MSISDN}", str(msisdn))        
        xml_data  = xml_data.replace("${PIN}", str(pin))
        self.client.post(
                    url=loginDns+"/api/standard-link/gp/endPoint?LOGIN=pretups&PASSWORD=1357&REQUEST_GATEWAY_CODE=USSDT&REQUEST_GATEWAY_TYPE=USSD&SERVICE_PORT=190&SOURCE_TYPE=USSD",
                    data=xml_data,
                    auth=None,
                    headers=simLastXTxnHeader,
                    name="GP_LASTXTXN_REPORT",
                    )
