API_PROJECT_DIR=api
API_PROJECT_NAME=app-monitor-api.war
WEB_PROJECT_DIR=web
WEB_PROJECT_NAME=app-monitor-web.war

# Try to build the project if it doesn't exists
function build(){
	PROJECT_ROOT_DIR=$1
	PROJECT_NAME=$2

	if [ ! -f $PROJECT_ROOT_DIR/target/$PROJECT_NAME ];then
		echo "The project has not been built yet, let me build for you..."
		mvn clean install -f $PROJECT_ROOT_DIR/pom.xml -DskipTests

		# Check the success of last command executed
		if [ $? -ne 0 ]; then
		    exit 1
		fi
	fi
}

build $API_PROJECT_DIR $API_PROJECT_NAME
build $WEB_PROJECT_DIR $WEB_PROJECT_NAME

docker-compose up
