# MolleFreeCam
A minecraft server plugin to allow players to use a freecam mode

No client-side mods are needed!

Tested on Paper-26.3-236.jar

## Installation
Download the current MolleFreeCam.jar and place it in your plugins folder, restart the server.

## Configuration
After the first start of the server a config.yml is created in the subdirectory ./plugins/MolleFreeCam/  
To change settings edit this file.

By default every player can use the freecam mode:

```java
default-allow: true
```

You can also only allow it by default for OPs only:

```java
op-allow: true
```

If you set both options to false, every player needs the permission:

```java
freecam.use
```

For this you would need a plugin to manage permissions, e. g. LuckPerms.

## Usage
The usage is quite simple, to enter freecam mode, a player has to type the command:

```
/float
```

To leave the freecam mode, the player can simply type the command again.

## How it works
When the player types the command, a replacement NPC is spawned at the players location and the players gamemode  
is changed to spectator.  
When leaving freecam mode, the player is teleported back to the NPC, the gamemode is changed back to survival and  
the NPC is removed again.

While in freecam mode, a configurable countdown (period) expires and when it hits 0, the freecam mode is aborted.  
After that, a configurable cooldown prevents the instant reuse of the command.  
Furthermore, the freecam mode is aborted when a configurable max distance to the starting point (NPC) is exceeded,  
or the NPC receives damage.

## Compiling on your own
The plugin is compiled and packaged with Apache Maven, so if you want to compile it on your own, it would be advisable
to install and use it.

One problem I stumbled upon was to use the right versions of java. As the project is configured now, it can be compiled  
with openjdk-21. However, the current paper implementation needs openjdk-25 to run.
For development I set the default java executable to openjdk-21 with:

```
sudo update-alternatives --config java
```

For paper I explicitly called the binary:

```
/usr/lib/jvm/java-25-openjdk-amd64/bin/java
```

With this setup I could compile without warnings/errors and the plugin seems to run fine.

## Motivation
This plugin is heavily inspired by [CyberGl1tch/LunarFreecam](https://github.com/CyberGl1tch/LunarFreecam/tree/master)

I used this plugin on my previous minecraft server running 1.21.x and wanted to use it again on a new server  
running 26.3 where it just crashed.

In order to have a working plugin again I looked at the core mechanics from LunarFreecam and wrote a new plugin  
based around them. At this point I want to thank CyberGl1tch for developing LunarFreecam in the first place.
