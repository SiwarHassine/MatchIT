FROM  openjdk:8
EXPOSE 8761
ADD /target/matchit-registry-server-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]