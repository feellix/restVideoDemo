# Video basic information detection 

This simple app provides the API for simple detection of video file statistics. It uses xuggle library as a main mechanism for video information extraction.

## Build
Using maven installation
> mvn clean install

__Warning! Xuggle library in version 5.4 sometimes crash JVM in 64bit environments__

## Run 
From artifact: 
> java -jar rest-video-demo-0.0.1-SNAPSHOT.jar

From maven
> mvn spring-boot:run

## API 
Application is secured with basic auth and exposes three API: 
* Create video information
> Content-Type=multipart/form-data; 
> POST /rest/video;
> Body: file
* Get all video information
> Content-Type=application/json; 
> GET /rest/video
* Get one video information by id. 
> Content-Type=application/json; 
> GET /rest/video/{id}