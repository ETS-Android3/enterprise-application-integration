FROM adoptopenjdk/openjdk11:latest
WORKDIR /
COPY . /
RUN apt update
RUN apt install maven -y
RUN mvn package
CMD ["java", "-jar", "target/idk-1.0.jar"]
