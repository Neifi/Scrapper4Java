FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD build/libs/Scrap-0.0.1-SNAPSHOT.jar scrap.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/scrap.jar"]