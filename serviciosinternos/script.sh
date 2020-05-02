#!/bin/bash

echo ''
echo '+------------------------------------------------------------------'
echo '|     MODELOS Y VALIDACION UNIVERSIDAD JAVERIANA                   '
echo '|     CREANDO SERVICIOS INTERNOS DEL BANCO                         '
echo '+------------------------------------------------------------------'
echo ''

BASE_DIR=$(pwd)
BASE_APP='serviciosinternos'

DIRECTORIO=$BASE_DIR/$BASE_APP

echo 'Directorio base de la aplicacion... ' $DIRECTORIO

echo 'Compilando fuentes y generando artefactos...'
mvn -f $DIRECTORIO/as400server/pom.xml clean package -Dmaven.test.skip=true
mvn -f $DIRECTORIO/apisaldos/pom.xml   clean package -Dmaven.test.skip=true
mvn -f $DIRECTORIO/sapservice/pom.xml  clean package -Dmaven.test.skip=true assembly:single 

echo 'Compilando y generando imagenes...'
docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=saldos-service:latest --rm=true $DIRECTORIO/apisaldos
docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=sap-service:latest    --rm=true $DIRECTORIO/sapservice
docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=as400-service:latest  --rm=true $DIRECTORIO/as400server

echo 'Imagenes generadas correctamente...'

echo '...................................'

echo 'Iniciando los contenedores...'
docker-compose -f $DIRECTORIO/sapservice/docker-compose.yml  up -d
docker-compose -f $DIRECTORIO/as400server/docker-compose.yml up -d
docker-compose -f $DIRECTORIO/apisaldos/docker-compose.yml   up -d

echo 'Contenedores iniciados correctamente...'