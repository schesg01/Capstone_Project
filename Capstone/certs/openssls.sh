#!/bin/bash

openssl genrsa -des3 -out client.key 2048
openssl req -out client.csr -key client.key -new
openssl genrsa -des3 -out ca.key 4096 
 openssl req -new -x509 -days 180 -extensions v3_ca -keyout ca.key -out ca.crt
openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out client.crt -days 180

 
 openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12
 openssl pkcs12 -in client.p12 -out client.pem -clcerts
