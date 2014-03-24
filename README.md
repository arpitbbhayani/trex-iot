T-rex IOT
=======================

####System Requirements

a. OS: Windows/Ubuntu ( Prefereable Ubuntu )
b. Other S/w:
i.  android adt
ii. IDE for java (preferable intelliJ Idea)
c. JVM:
The repo can run on any java but having an uniformity on java1.7 will save effort in handling compatibility issues. (preferable java-oracle-1.7)

####Installation:
a. Setup repository
i.  Create a folder iot.
ii. from that folder type following command
> git clone https://github.com/arpitbbhayani/trex-iot.git

b. Setup Android ADT
i. Download adt from following link
http://developer.android.com/sdk/index.html
This will have eclipse with ADT. You can directly start eclipse from this ADT.

    c. Setup Java
	        > sudo add-apt-repository ppa:webupd8team/java
	        > sudo apt-get install -y oracle-java7-installer

	    When asked for java always provide the following path:
		    /usr/lib/jvm/java-7-oracle

####Projects:
    a. trex-iot-platform:
	    Java project which you can directly import in intellij idea. All
the platform based code should go here.

    b. trex-iot-app:
	    This project can be directly be added to eclipse in the ADT bundle
downloaded from the link given above. All the andoid code should go here.
