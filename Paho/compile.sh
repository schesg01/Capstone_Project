rm ./src/pi_zero/*.class

javac -cp "./src:./src/pi_zero:/home/pi/paho/org.eclipse.paho.client.mqttv3-1.2.1-20180313.040548-111.jar:bcpkix-jdk15on-159.jar:bcprov-ext-jdk15on-159.jar:bctls_jdk15on-159.jar" src/pi_zero/Main.java
