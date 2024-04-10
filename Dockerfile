FROM openjdk:17
EXPOSE 8080
ADD target/closet.jar closet.jar
ENTRYPOINT ["java","-jar","/closet.jar" ]