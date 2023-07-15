FROM  openjdk:8
EXPOSE 8762
ADD /target/matchit-calculator-0.0.1-SNAPSHOT.jar calculator.jar
ENTRYPOINT ["java", "-jar", "calculator.jar"]