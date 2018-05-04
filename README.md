# Capstone_Project
460 IoT
### Building
There are two folders, Paho and Kura, whcih are the two main programs/frameworks used. Kura is the server/broker, and Paho is the client.
To build the project, you will need
* __(WIP)__ For Kura, an installation of Kura on a Raspberry Pi 3 or similar.
* For Paho, all you need is Java 1.8 and a recent version of Paho. All other libraries are included.
* Each folder is also an Eclipse project directory. 
* (Optional) On the Raspberry Pi acting as the server, install [Pi-btnap](https://github.com/bablokb/pi-btnap) to enable Bluetooth TCP/IP communication.
* Also on the Raspberry Pi, there are also some config files that go into /etc :
  * dhcpd.conf goes into /etc/dhcp
  * btnap.conf goes into /etc
 * On the Paho device, run ./compile.sh
 
 ### Running
 * On the Paho device, run ./run.sh 5 0
 * The first argument is the time between data transfers, the second is a flag that generates new test data (Not working) 0 = Don't generate, 1 = Generate
 
