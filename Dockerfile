FROM maven:3-jdk-8 as build

COPY . /home/app
WORKDIR /home/app
RUN mvn package -Dmaven.javadoc.skip

FROM dejankovacevic/bots.runtime:2.10.3

COPY --from=build /home/app/target/gitlab.jar     /opt/gitlab/gitlab.jar
COPY ./gitlab.yaml           /etc/gitlab/gitlab.yaml

WORKDIR /opt/gitlab

EXPOSE  8080 8081
