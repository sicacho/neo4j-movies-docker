FROM java:8

MAINTAINER trustme013@gmail.com

VOLUME /logs

EXPOSE 8080

ENV USER_NAME movie

ENV APP_HOME /home/$USER_NAME/app

RUN useradd -ms /bin/bash $USER_NAME

RUN mkdir $APP_HOME

ADD target/movies-java-spring-data-neo4j-4-1.0-SNAPSHOT.jar $APP_HOME/movies-java-spring-data-neo4j-4-1.0-SNAPSHOT.jar

RUN chown $USER_NAME $APP_HOME/movies-java-spring-data-neo4j-4-1.0-SNAPSHOT.jar

USER $USER_NAME

WORKDIR $APP_HOME

RUN bash -c 'touch movies-java-spring-data-neo4j-4-1.0-SNAPSHOT.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","movies-java-spring-data-neo4j-4-1.0-SNAPSHOT.jar"]
