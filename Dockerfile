FROM dejankovacevic/bots.runtime:2.10.3

COPY target/gitlab.jar     /opt/gitlab/gitlab.jar
COPY gitlab.yaml           /etc/gitlab/gitlab.yaml

WORKDIR /opt/gitlab

EXPOSE  8080 8081
