#!/bin/bash

# Use this script to install and run Bluesnail platform, or create skeleton of a plugin.
# Note that the platform must be installed before compile any plugin.

PROJECT_PATH=$(pwd)

# Directory path

PLATFORM_PATH="${PROJECT_PATH}/platform"
APPLICATIONS_PATH="${PROJECT_PATH}/applications"
EXTENSIONS_PATH="${PROJECT_PATH}/extensions"
TEMPLATES_PATH="${PROJECT_PATH}/templates"

# Platform jar file path

PLATFORM_JAR="${PLATFORM_PATH}/target/bluesnail-platform-1.0-SNAPSHOT.jar"

# Information for the creation of plugin

APP_NAME=""
PARENT_NAME=""

usage()
{
	echo "Usage : ./bluesnail <option(s)>"
	echo "Options :"

	echo "	-i"
	echo "		Install the platform and compile all existing plugin (applications and extensions)"
	echo 

	echo "	-r"
	echo "		Run the platform, if the platform or a plugin is not installed, install it"
	echo 

	echo "	-c <plugin_name> [-p <parent_plugin>]"
	echo "		Create a plugin. If a parent plugin is specified, the plugin become an extension,"
	echo "		otherwise, the plugin become an appliction (autorun plugin)"
	echo 

	echo "	-h"
	echo "		Print help"
	echo 

	exit $1
}

install_platform()
{
	cd ${PLATFORM_PATH}
	
	echo
	echo "[INFO] CREATION OF PLATFORM JAR..."
	mvn package
	
	if [ ! $? -eq 0 ]; then
		echo "[ERROR] Failed to install the platform"
		exit 1
	fi
}

install_plugin()
{
	cd $1
	
	for dir in $(ls)
	do
		echo
		echo "[INFO] CREATION OF \"${dir}\" JAR... "
		cd ${dir}
		mvn package
		
		if [ ! $? -eq 0 ]; then
			echo "[ERROR] Failed to install the plugin \"$1/${dir}\""
			exit 1
		fi
		
		cd $1
	done
}

install_all()
{		
	# Creation of platform jar
	
	install_platform

	# Creation of applications jar

	install_plugin "${APPLICATIONS_PATH}"
	
	# Creation of extensions jar
	
	install_plugin "${EXTENSIONS_PATH}"

	# Add empty line and return to the project path...
	cd ${PROJECT_PATH}
	echo
}

check_install()
{
	# Check platform installation
	
	if [ ! -f ${PLATFORM_JAR} ] 
	then
		install_platform
	fi
	
	# Check application installation
	
	cd ${APPLICATIONS_PATH}	
	
	for dir in $(ls)
	do
		if [ ! -f "${APPLICATIONS_PATH}/${dir}/target/${dir}-1.0-SNAPSHOT.jar" ]; then
			install_plugin ${APPLICATIONS_PATH} 
		fi
	done
	
	# Check extension installation
	
	cd ${EXTENSIONS_PATH}
	
	for dir in $(ls)
	do
		if [ ! -f "${EXTENSIONS_PATH}/${dir}/target/${dir}-1.0-SNAPSHOT.jar" ]; then
			install_plugin ${EXTENSIONS_PATH} 
		fi
	done
	
	# Add empty line and return to the project path...
	cd ${PROJECT_PATH}
	echo
}

create_plugin()
{
	if [ -n "${PARENT_NAME}" ]
	then
		echo "-----------------------------------------------"
		echo "CREATION OF EXTENSION : ${APP_NAME}"
		echo "-----------------------------------------------"

		# Check if the parent application exists
		cd ${APPLICATIONS_PATH}

		if [ ! -d "${PARENT_NAME}" ]
		then
			echo "[ERROR] The application ${PARENT_NAME} does not exist"
			exit 1
		fi

		cd ${EXTENSIONS_PATH}

		if [ ! -d "${APP_NAME}" ]
		then
			# Step 1 : create the directory structure

			mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart \
									-DgroupId=com.alma.extension \
									-DartifactId=${APP_NAME} \
									-DinteractiveMode=false

			# Step 2 : replace the pom.xml with the template

			cd ${APP_NAME}
			rm pom.xml
			cp ${TEMPLATES_PATH}/pom.xml.extension-template ./pom.xml

			# Step 3 : set the app name and the parent app name in the pom.xml

			sed -i "s/APP_NAME/${APP_NAME}/g" ./pom.xml
			sed -i "s/PARENT_NAME/${PARENT_NAME}/g" ./pom.xml
			sed -i "s/PARENT_JAR/${PARENT_NAME}-1.0-SNAPSHOT.jar/g" ./pom.xml

			echo
			echo "[INFO] The plugin has been created in the directory : ${EXTENSIONS_PATH}/${APP_NAME}"
			echo "[INFO] Note : The parent plugin (${PARENT_NAME}) must be compiled before using this extension"
		else
			echo "[ERROR] This plugin already exists"
		fi

	else
		echo "-----------------------------------------------"
		echo "CREATION OF APPLICATION : ${APP_NAME}"
		echo "-----------------------------------------------"

		cd ${APPLICATIONS_PATH}

		if [ ! -d "${APP_NAME}" ]
		then
			# Step 1 : create the directory structure

			mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart \
									-DgroupId=com.alma.application \
									-DartifactId=${APP_NAME} \
									-DinteractiveMode=false

			# Step 2 : replace the pom.xml with the template

			cd ${APP_NAME}
			rm pom.xml
			cp ${TEMPLATES_PATH}/pom.xml.application-template ./pom.xml

			# Step 3 : set the app name in the pom.xml

			sed -i "s/APP_NAME/${APP_NAME}/g" ./pom.xml

			echo
			echo "[INFO] The plugin has been created in the directory : ${APPLICATIONS_PATH}/${APP_NAME}"
		else
			echo "[ERROR] This plugin already exists"
		fi
	fi

	# Add empty line and return to the project path...
	cd ${PROJECT_PATH}
	echo
}

####################################
#          MAIN PROCESS            #
####################################

# Print usage if no option has been specified

if [ $# -eq 0 ]; then
	usage 1
fi

# Create directory if they don't exist

if [ ! -d "${APPLICATIONS_PATH}" ]
then
	echo "[INFO] Create the applications directory"
	mkdir ${APPLICATIONS_PATH}
fi

if [ ! -d "${EXTENSIONS_PATH}" ]
then
	echo "[INFO] Create the extensions directory"
	mkdir ${EXTENSIONS_PATH}
fi

# Get option (see usage)

while getopts ":irc:p:h" option
do
	case ${option} in
		i)	
			install_all

			exit 0
			;;
		
		r)
			check_install

			cd ${PLATFORM_PATH}
			java -jar ${PLATFORM_JAR}
			cd ${PROJECT_PATH}

			exit 0
			;;

		c)	
			APP_NAME=${OPTARG}
			;;
		p)	
			PARENT_NAME=${OPTARG}
			;;
		h)	
			usage 0
			;;
		:)
			echo "[ERROR] Missing argument for the option -${OPTARG}"
			echo 
			usage 1
			;;
		\?)
			echo "[ERROR] -${OPTARG} : invalid option"
			echo 
			usage 1
			;;
	esac
done

# Create the plugin if an app name has been specified

if [ -n "${APP_NAME}" ]
then
	check_install
	create_plugin
fi

exit 0
