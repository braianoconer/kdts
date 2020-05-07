#!/bin/bash

DOCKER_REGISTRY=${1:-docker-registry.pro.com}

VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
ARTIFACT=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)

if [[ "$VERSION" == *-SNAPSHOT ]]
then
	DOCKER_TAG=latest
else
	DOCKER_TAG="$VERSION"
fi

DOCKER_IMAGE="$DOCKER_REGISTRY"/pro/"$ARTIFACT":"$DOCKER_TAG"


export DOCKER_IMAGE
