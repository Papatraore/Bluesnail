# Bluesnail
BlueSnail is a manage plugin framework. It allows to develop any kind of application that must respect the rules and conventions specified by this framework. This project contains an example of application which can be ran on this framework (roger-runner) and different extensions of it. We distinguish two types of plugin : application (autorun plugin) and extension (need an application to run).

Structure of the project :
* platform : framework which manages the plugin
* applications : autorun plugin which is ran immediately by the framework
* extensions : plugin which extends an application 
* documentation : technical and user documentation
* templates : contains templates for the creation of plugin

## Prerequisites
* Java (version 1.7)
* Maven (version 3)

## Installation and execution
At the root of the project please run one of these following commands :

* To compile all the project
```bash
./bluesnail.sh -i
```

* To run the application (if the application, an extension or the platform is not installed, this command will do it)
```bash
./bluesnail.sh -r
```

* To print help
```bash
./bluesnail.sh -h
```

## Create a new plugin
At the root of the project please run one of these following commands :
* To create an application
```bash
./bluesnail.sh -c <app-name>
```

* To create an extension of existing application
```bash
./bluesnail.sh -c <extension-name> -p <app-name>
```

**Important** : If you create a plugin, some modifications may be required in the config.txt file in the platform. For more informations, please refer to the documentation (section "Développer un plugin".
