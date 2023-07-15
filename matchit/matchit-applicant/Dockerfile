FROM  openjdk:8
EXPOSE 8762
ADD /target/matchit-applicant-0.0.1-SNAPSHOT.jar applicant.jar
ENTRYPOINT ["java", "-jar", "applicant.jar"]