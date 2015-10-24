# APP-Monitor

http://rodrigoramalho-appmonitor.rhcloud.com/

## Jenkins

This application uses Jenkins to build and deploy application on Openshift.

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

* Download WildFly 9.0.1 from
  http://download.jboss.org/wildfly/9.0.1.Final/wildfly-9.0.1.Final.zip
  and unzip.
* Start WildFly as: `./bin/standalone.sh`
* Deploy application WAR to WildFly: `mvn wildfly:deploy`
* Install to local Maven repository: `mvn install`
	* repo in '~/.m2/repository'
* Deploy SNAPSHOT version to local Nexus
	* Only binary: `mvn deploy`
	* Binary, including javadoc and sources jars: `mvn deploy -P release`
* Perform RELEASE : `mvn release:prepare release:perform`
	* versions changes and tag are committed to github
	* all tests run
	* WAR is deployed to Wildfly
	* deployment to local Nexus including WAR, javadoc and sources
* Alternatively to deploy RELEASE version (e.g. 2.0) to Nexus without GitHub integration
	* `mvn versions:set -DnewVersion=2.0`
	* `mvn deploy -P release`
