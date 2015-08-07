CUR_DIR=$PWD
PROJECT_DIR=$CUR_DIR/..
APP_FILE=$PROJECT_DIR/target/app-monitor.war

# Try to build the project if it doesn't exists
if [ ! -f $APP_FILE ];then
	echo "The project has not been built yet, let me build for you..."
	mvn clean install -f $PROJECT_DIR/pom.xml

	# Check the success of last command executed
	if [ $? -ne 0 ]; then
	    exit 1
	fi
fi

# Put the application in a docker context
mkdir tmp
cp $APP_FILE tmp/

echo "Generating app-monitor docker tag"
docker build --tag=app-monitor .

rm -rf tmp

echo "Running the container"
docker run -it -p 8080:8080 app-monitor
