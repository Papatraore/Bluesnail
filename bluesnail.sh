#!/bin/bash

# Use this script to install and run Bluesnail platform, or create skeleton of a plugin.
# Note that the platform must be installed before compile any plugin.

PROJECT_PATH=$(pwd)

PLATFORM_PATH="${PROJECT_PATH}/platform"
PLATFORM_JAR="${PLATFORM_PATH}/target/bluesnail-platform-1.0-SNAPSHOT.jar"

APPLICATIONS_PATH="${PROJECT_PATH}/applications"
EXTENSIONS_PATH="${PROJECT_PATH}/extensions"
TEMPLATES_PATH="${PROJECT_PATH}/templates"

APP_NAME=""
PARENT_APP_NAME=""
PARENT_APP_JAR=""

usage()
{
	echo "Usage : ./bluesnail <option(s)>"
	echo "Options :"

	echo "	-i"
	echo "		Install the platform"
	echo ""

	echo "	-r"
	echo "		Run the platform, if the platform is not installed, install it"
	echo ""

	echo "	-c <plugin_name> [-p <parent_plugin>]"
	echo "		Create a plugin. If a parent plugin is provided, the plugin become an extension,"
	echo "		otherwise, the plugin become an appliction (autorun plugin)"
	echo ""

	echo "	-h"
	echo "		Print help"
	echo ""
}

install_platform()
{
	cd ${PLATFORM_PATH}
	mvn package
	cd ..
}

create_plugin()
{
	
	#mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart -DgroupId=org.ccm.maven -DartifactId=helloworld -DinteractiveMode=false
}

while getopts ":irc:h:" option
do
	case ${option} in
		i)
			install_platform
			;;
		r)
			if [ ! -f ${PLATFORM_JAR} ] 
			then
				install_platform
			fi

			java -jar ${PLATFORM_JAR}
			;;
		c)
			create_plugin
			;;
		h)
			usage
			;;
		:)
			echo "Missing argument for the option ${OPTARG}"
			echo ""
			usage
			exit 1
			;;
		\?)
			echo "${OPTARG} : invalid option"
			echo ""
			usage
			exit 1
			;;
	esac
done