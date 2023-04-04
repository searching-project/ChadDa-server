FROM adoptopenjdk/openjdk11
MAINTAINER search363
CMD ["./gradlew","clearn","package"]
ARG JAR_FILE=./build/libs/instagram-searching-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]