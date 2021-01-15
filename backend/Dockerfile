FROM openjdk:8-jre-alpine

EXPOSE 8080

COPY kotlin-server.jar /root/kotlin-server.jar

WORKDIR /root

CMD ["java", "-server", "-Xms4g", "-Xmx4g", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "kotlin-server.jar"]