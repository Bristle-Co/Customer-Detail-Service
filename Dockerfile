FROM openjdk:17
MAINTAINER AndersonHsieh
RUN mkdir app
WORKDIR /app
COPY target/customer-detail-service-0.0.1.jar app/customer-detail-service-0.0.1.jar
ENTRYPOINT ["java","-jar","app/customer-detail-service-0.0.1.jar"]
EXPOSE 8085
EXPOSE 9095