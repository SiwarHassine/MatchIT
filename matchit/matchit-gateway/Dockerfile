FROM  openjdk:8
EXPOSE 8762
ADD /target/matchit-gateway-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT ["java", "-jar", "gateway.jar"]