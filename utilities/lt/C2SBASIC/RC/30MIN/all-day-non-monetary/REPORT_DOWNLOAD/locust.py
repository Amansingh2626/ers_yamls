import time
import requests
import csv
import random
import os
from locust import HttpUser, task, between ,constant,constant_pacing
from locust.user import wait_time



loginDns = os.getenv('SERVER_FQDN') 
fromDate = ""
toDate = ""
loginUser = ""
loginPass = ""

def read_user_credentials_from_csv():
    with open("/home/locust/data/downloadC2SReport.csv", 'r') as file:
        lines = [tuple(line) for line in csv.reader(file)] 
    return lines    
      

class downloadC2SCsv(HttpUser): 
    def __init__(self,parent):
        super(downloadC2SCsv,self).__init__(parent)
        self.authToken=""

    wait_time = constant_pacing(2)

    def on_start(self):
        lines = read_user_credentials_from_csv()
        chosen_rows = random.choice(lines) 
        loginUser = chosen_rows[0]
        loginPass = chosen_rows[1]
        loginHeaders = {"Accept": "application/json" , "content-type": "application/json","content-type": "application/json"}
        myData = '{"channel":"SEAMLESS-UNIFIED","userId":'+loginUser+',"password":'+loginPass+',"sendOTP":"false"}'
        self.authToken = requests.post(loginDns+"/login-backend", data=myData, headers=loginHeaders).headers['authorization']

    @task 
    def downloadC2SCsv(self):   
        lines = read_user_credentials_from_csv()
        chosen_rows = random.choice(lines) 
        loginUser = chosen_rows[0]
        fromDate = chosen_rows[2]
        toDate = chosen_rows[3]
        csvDownloadHeader = {
                    "Accept": "application/json" , 
                    "Referer": loginDns+"/login" , 
                    "content-type": "application/json",
                    "content-type": "application/json",
                    "Origin": loginDns,
                    "Connection": "keep-alive",
                    "authorization":self.authToken
                    }
                   
        csvDownloadData = '{"request":{"size":"100","zone":"ALL","Domain":"ALL","domainType":"ALL","transactionType":"ALL","fromDate":"'+fromDate+'","toDate":"'+toDate+'","column_names":null,"userId":"'+loginUser+'","reportName":"C2S transfer details","page":"0"}}'                
        self.client.post(
                    url=loginDns+"/api/bi-engine/downloadCSV",
                    data=csvDownloadData,
                    auth=None,
                    headers=csvDownloadHeader,
                    name="Download_C2S_Report",
                    )
