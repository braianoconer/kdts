#!/bin/bash

PROJECT_DIR=$(dirname $0)




#source "$PROJECT_DIR"/docker_properties.sh
DOCKER_IMAGE="docker-registry.pro.com/pro/translator:latest"




DOCKER_BUILDKIT=1 docker build -t "$DOCKER_IMAGE" "$PROJECT_DIR"
