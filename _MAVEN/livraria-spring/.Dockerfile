# we are extending everything from tomcat:8.0 image ...
FROM tomcat:8.5.16-jre8-alpine

LABEL Eduardo A Oliveira

EXPOSE 8088

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ./target/livraria-spring-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh, "run"]