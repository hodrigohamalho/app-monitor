FROM jboss/wildfly

MAINTAINER Rodrigo Ramalho da Silva - hodrigohamalho@gmail.com

ENV DEPLOY_DIR $JBOSS_HOME/standalone/deployments/

RUN mvn clean install -DskipTests

ADD target/app-monitor.war $DEPLOY_DIR

