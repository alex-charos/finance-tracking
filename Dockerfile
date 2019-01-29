FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/finance*.jar app.jar
CMD java -jar -Dserver.port=$PORT app.jar
