FROM gradle:7.2.0-jdk8 as build
WORKDIR /usr/app
COPY . /usr/app/
RUN gradle build 

FROM tomee:9.0.0-webprofile
COPY --from=build /usr/app/build/libs/mesures.war /usr/local/tomee/webapps/mesures.war
