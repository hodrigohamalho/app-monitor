# APP-Monitor

http://rodrigoramalho-appmonitor.rhcloud.com/

## Jenkins

https://jenkins-appmonitor.rhcloud.com

## Technologies:

	* Java
	* Maven (dependency and build)
	* JPA (Persistence)
	* CDI
	* JAX-RS (Rest)
	* JUnit (Test)
	* Arquillian (Test)
	* HTML 5 (View)
	* AngularJS (View)
	* Docker

## Database

H2 in memory database.

## Application Server

[Wildfly](http://wildfly.org/)

NOTE: After the server is installed, set the JBOSS_HOME variable to the installation directory. eg:

```
export JBOSS_HOME=/opt/wildfly-9.0.1.Final/
```

## Docker

The docker script downloads and configures JDK and Wildfly to run the application app-monitor. It makes all adjustments and deploy the application that is exposed on 8080 port.

To run the script you just need to execute start.sh located on docker directory.

```
./docker/start.sh
```

This script tries to build the app from your local repository, if it doesn't work the app-monitor.war package will be downloaded from github and deployed directly.

Finally, access: http://localhost:8080/app-monitor
