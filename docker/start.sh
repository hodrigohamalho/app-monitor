CUR_DIR=$PWD
PROJECT_DIR=$CUR_DIR/..
APP_FILE=$PROJECT_DIR/target/app-monitor.war

# Put the application in a docker context
mkdir tmp
cp $APP_FILE tmp/

echo "Generating app-monitor docker tag"
docker build --tag=app-monitor .

rm -rf tmp

echo "Running the container"
docker run -it -p 8080:8080 app-monitor
