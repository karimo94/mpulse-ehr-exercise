# build stage
FROM maven:3.8.3-openjdk-17 AS build
# create working directory
RUN mkdir /app
# copy over all contents including pom.xml
COPY /mpulse_ehr_demo /app
# set working directory
WORKDIR /app
RUN mvn clean package

# run stage
# base img
FROM openjdk:17
# new app dir
WORKDIR /app
# copy app files from host to img fs
COPY --from=build /app/target/mpulse_ehr_demo-1.0-SNAPSHOT.jar /app/mpulse_ehr_demo-1.0-SNAPSHOT.jar
# run the jar executable
ENTRYPOINT ["java", "-jar", "mpulse_ehr_demo-1.0-SNAPSHOT.jar"]