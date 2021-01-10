FROM adoptopenjdk/openjdk11:latest
WORKDIR /
COPY . /
RUN apt update
RUN apt install maven -y
RUN mvn package
CMD ["java", "-jar", "target/Statboard_adapter-1.0-SNAPSHOT.jar"]
