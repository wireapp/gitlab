#!/usr/bin/env bash
name=gitlab
env=prod
tag=0.1.0

docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
mvn package -DskipTests=true -Dmaven.javadoc.skip=true
docker build -t $DOCKER_USERNAME/${name}:${tag} .
docker push $DOCKER_USERNAME/${name}
#kubectl delete pod -l name=${name} -n ${env}
#kubectl get pods -l name=${name} -n ${env}

