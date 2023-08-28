# ers-link-simulator multi/single-instance setup/configuration

### Overview

link-simulator is a generic component. It can be used with multiple link components, if the request response is mapped in the config of link-simulator as per the corresponding link component.

Suppose we have two links (ers-cislink- & ers-sdplink) that want to use this link-simulator.

Below is the configuration for multi-instance-setup & single-instance-setup of link-simulator.

######Note: link-simulator-resources.properties file has the information regarding the port on which the simulator has to be started. This file has to be present for spawning an instance of link-simulator.



#### multi-instance-setup 

First we check how many folders are inside link-simulator config that have link-simulator-resources.properties.
/opt/seamless/confg/link-simulator/ers-cislink-simulator/link-simulator-resources.properties
/opt/seamless/confg/link-simulator/ers-sdplink-simulator/link-simulator-resources.properties
etc...

For each of those folder we spawn an instance.

During creating an instance, two folders are created, if they are already not present.
1. pidDir="/var/seamless/system/${component}"
2. logDir="/var/seamless/log/link-simulator/${component}"

##### Configuration
There are few places where the configuration will change for every new folder/instance added.
1. in link-simulator-resources.properties change 
     * server.port: 8112
     * http.port=8212
2. In log4j.properties
     * log4j.appender.MAINLOG.File=/var/seamless/log/link-simulator/${component}/link-simulator.log
3. files 
     * link-simulator-rest.properties
     * link-simulator-soap.properties

In all the examples ${component} can be a folder name i.e  ers-cislink-simulator, ers-sdplink-simulator etc. 

####single-instance-setup 

If there is a link-simulator-resources.properties file in config folder itself (i.e /opt/seamless/confg/link-simulator) multi-instance setup wont be triggered. (this ensures backward compatibility).