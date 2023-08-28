#!/bin/bash

oc create configmap ers-common-config --from-file=config --namespace ers-prod

oc create configmap asia-dhaka-tz-config --from-file=/usr/share/zoneinfo/Asia/Dhaka --namespace ers-prod
