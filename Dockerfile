FROM jboss/wildfly

MAINTAINER Rodrigo Ramalho da Silva - hodrigohamalho@gmail.com

USER 1000

ADD docker/postgresql-9.4-1205.jdbc4.jar $JBOSS_HOME/standalone/deployments/

RUN curl -O http://jenkins-devops.cloud.devops.org/job/app-monitor-build/lastStableBuild/lab.hack\$app-monitor/artifact/lab.hack/app-monitor/1.0.0/app-monitor-1.0.0.war \
    && mv app-monitor-1.2-SNAPSHOT.war $JBOSS_HOME/standalone/deployments/
