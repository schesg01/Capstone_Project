#!/bin/bash
echo "Starting MQTT subscriber..."
mosquitto_sub -h localhost -t test -p 8883 --cafile /etc/mosquitto/ca_certificates/ca.crt --insecure

