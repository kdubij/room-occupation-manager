#!/usr/bin/env bash

mvn clean package docker:build

docker-compose up