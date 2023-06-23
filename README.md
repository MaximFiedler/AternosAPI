<p align="center">
<a href="https://github.com/MaximFiedler/AternosAPI/blob/master/LICENSE"><img src="https://img.shields.io/github/license/MaximFiedler/AternosAPI.svg" alt="License"></a>  
<a href="https://github.com/MaximFiedler/AternosAPI/releases"><img src="https://img.shields.io/github/v/tag/MaximFiedler/AternosAPI.svg" alt="Version"></a>  
</p>

![img](https://media.discordapp.net/attachments/1052241511795937381/1092801532573003787/Neues_Projekt_17_2.png?width=1439&height=576)


# Get the newest servers from the Aternos server list using the API


### EXAMPLES
```java
int serverAmount = 10;
for(String serverIP : AternosAPI.getNewestServersFromList(serverAmount)) {
  System.out.println(serverIP);
}
```
Output:
```
speedrunheh.aternos.me
undefinedls.aternos.me
abnormalmcSMP.aternos.me
popnetwork.aternos.me
LifeStealRomaniaoRTb.aternos.me:18381
Hardcore_block.aternos.me
Astheticpixel.aternos.me:20784
abnormalmcSMP.aternos.me
Lab_1.aternos.me:41616
Astheticpixel.aternos.me:20784
```

--------------------------------------------------------------------

### How to use AternosServer class

Get AternosServer object:
```java
AternosServer aternosServer = AternosAPI.getAternosServer(136468);
```
Now you can get the data from the specific aternos server:
```java
aternosServer.getOwner();
aternosServer.getIp();
aternosServer.getEdition();
aternosServer.getDiscordInvite();
```

**getTitle()** and **getDescrition()** are not implemented yet! They will always return null!

--------------------------------------------------------------------



