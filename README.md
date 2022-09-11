this service is used to store data of all the users and employees in Bristle Co., Ltd
# Deployment

### Build into docker image
1. cd to $PROJECT_ROOT
2. cd to ./configuration and do git pull to make sure configuration submodule is at newest commit
3. `mvn -Dcustomer-detail-service=localhost -DDB_PASSWORD=Microservice@39909204 -DDB_USERNAME=microservice -DHOST=localhost -Dorder-service=localhost -Dproduction-ticket-service=localhost -Duser-service=localhost clean package`

   maven package execute tests and we want it to be able to load application context, thus the HOST environment variable is needed
   when running mvn package, there will be a .jar under $PROJECT_ROOT/target/customer-detail-service-<version>.jar. The docker file then uses this location to copy into the image
4. `docker build -t customer-detail-service:<version> .`

# Running this service locally
There are some preconfigured environment variables that need to be fulfilled and they are listed below

### running in Intellij from source code
edit run configurations and copy-paste below code block to add environment variables

      customer-detail-service=localhost;DB_PASSWORD=Microservice@39909204;DB_USERNAME=microservice;HOST=localhost;order-service=localhost;production-ticket-service=localhost;user-service=localhost

### running in terminal from packaged .jar
Once a jar is packaged, assuming the name of the .jar file is `customer-detail-service-0.0.1.jar` we can go to its directory and run

      java -jar customer-detail-service-0.0.1.jar -Dcustomer-detail-service=localhost -DDB_PASSWORD=Microservice@39909204 -DDB_USERNAME=microservice -DHOST=localhost -Dorder-service=localhost -Dproduction-ticket-service=localhost -Duser-service=localhost

### running with docker from image
      docker pull andersonhsieh0330/customer-detail-service:<version>

assuming the image repository is customer-detail-service and tag(<version>) is 0.0.1

      docker run -p 8085:8085 -p 9095:9095 -ti --env customer-detail-service=localhost --env DB_PASSWORD=Microservice@39909204 --env DB_USERNAME=microservice --env HOST=host.docker.internal --env order-service=localhost --env production-ticket-service=localhost --env user-service=localhost customer-detail-service:0.0.1