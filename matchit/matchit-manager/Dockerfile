FROM  openjdk:8
EXPOSE 8762
ADD /target/matchit-manager-0.0.1-SNAPSHOT.jar manager.jar
ENTRYPOINT ["java", "-jar", "manager.jar"]