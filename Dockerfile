FROM openjdk:21
COPY target/git-proxy-0.0.1-SNAPSHOT.jar git-proxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/git-proxy-0.0.1-SNAPSHOT.jar"]