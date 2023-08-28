import time
import requests
import csv
import random
import os
from locust import HttpUser, task, between ,constant,constant_pacing
from locust.user import wait_time

loginDns = os.getenv('SERVER_FQDN')

def read_user_credentials_from_csv():
    with open("/home/locust/data/gp_otherbalance_data.csv", 'r') as file:
        lines = [tuple(line) for line in csv.reader(file)] 
    return lines    

csvData = read_user_credentials_from_csv()


class simOtherBalance(HttpUser): 
    
    wait_time = constant_pacing(1)
    @task 
    def simOtherBalance(self):               
        with open('/home/locust/data/gp_otherbalance_body.xml', 'r') as xml_fh:
            xml_data = xml_fh.read()              
        simOtherBalanceHeader = {
                    'content-type': 'application/xml',
                    }
        
        chosen_rows = random.choice(csvData)
        login = chosen_rows[0]        
        password = chosen_rows[1]        
        msisdn = chosen_rows[2]
        xml_data  = xml_data.replace("${LOGIN}", str(login)) 
        xml_data  = xml_data.replace("${PASSWORD}", str(password)) 
        xml_data  = xml_data.replace("${MSISDN}", str(msisdn))        
        self.client.post(
                    url=loginDns+"/api/standard-link/gp/endPoint?LOGIN=pretups&PASSWORD=1357&REQUEST_GATEWAY_CODE=USSDT&REQUEST_GATEWAY_TYPE=USSD&SERVICE_PORT=190&SOURCE_TYPE=USSD",
                    data=xml_data,
                    auth=None,
                    headers=simOtherBalanceHeader,
                    name="GP_OTHERBALANCE_REPORT",
                    )
