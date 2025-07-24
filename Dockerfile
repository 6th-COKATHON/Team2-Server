FROM eclipse-temurin:21-jdk
RUN rm -f project.jar
COPY ./build/libs/*SNAPSHOT.jar project.jar
ENTRYPOINT ["java", "-jar", "project.jar", "--spring.profiles.active=prod"]