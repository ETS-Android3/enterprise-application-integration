FROM adoptopenjdk/openjdk11:latest
WORKDIR /
COPY . /
RUN apt update
RUN apt install maven -y
CMD ["./mvnw", "spring-boot:run"]
