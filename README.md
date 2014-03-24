T-rex IOT
=======================

##System Requirements

###Operating System
Windows/Ubuntu ( Prefereable Ubuntu )
###Other S/w:
1. Android ADT Bundle
2. IDE for java (preferable intelliJ Idea)
3. Java:
The repo can run on any java but having an uniformity on java1.7 will save effort in handling compatibility issues. (preferable java-oracle-1.7)
    
##Installation:

###Setup repository
Create a folder iot. From that folder execute the following command
```sh
    $ git clone https://github.com/arpitbbhayani/trex-iot.git
```

####Import trex-iot-platform in IntelliJ IDEA
Open intellij idea and follow the following path <br/> `File > New Project > Project Location > select trex-iot-platform folder in the folder trex-iot/`<br/>
This will import the project in your IntelliJ Idea IDE.

###Setup Android ADT
Download adt from following link
`http://developer.android.com/sdk/index.html`
This will have eclipse with ADT. You can directly start eclipse from this ADT.

###Setup Java

```sh
    $ sudo add-apt-repository ppa:webupd8team/java
    $ sudo apt-get install -y oracle-java7-installer`
```
When asked for java always provide the following path : `/usr/lib/jvm/java-7-oracle`
