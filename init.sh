#!/bin/bash

dti=$(date '+%d/%m/%Y %H:%M:%S')

echo '+------------------------------------------------------------------'
echo '|     MODELOS Y VALIDACION UNIVERSIDAD JAVERIANA                   '
echo '|     FECHA    : '$dti
echo '|     AUTORES  : EQUIPO 5                                        '
echo '|     DESCRIPC : CREACION E INICIO DE CONTENEDORES DEL BANCO ABC '
echo '+------------------------------------------------------------------'

echo 'Deteniendo contenedores en ejecucion...'
docker stop $(docker ps -a -q --filter="name=service")
docker stop $(docker ps -a -q --filter="name=db_")
docker stop $(docker ps -a -q --filter="name=db")

echo 'Eliminando contenedores'
docker rm $(docker ps -a -q --filter="name=service")
docker rm $(docker ps -a -q --filter="name=db_")
docker rm $(docker ps -a -q --filter="name=db")

echo 'Eliminando Imagenes'
docker rmi $(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'service')
docker rmi $(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'db_')
docker rmi $(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'ndb')

# Eliminando redes en caso de que existan
echo 'Eliminando redes...'
docker network rm $(docker network ls | grep "backend" | awk '/ / { print $1 }')

# Creando la red comun para todos los contenedores
echo 'Creando red backend'
docker network create --driver bridge backend

# Script para crear los servicios internos del banco
sh serviciosinternos/script.sh

# Contruyendo servicios externos al banco
sh serviciosexternos/script.sh

# Construyendo servicio de convenios
echo 'Compilando fuentes y generando artefactos de convenios...'
mvn -f apiconvenios/pom.xml clean package -Dmaven.test.skip=true

docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=convenios-service:latest --rm=true apiconvenios/
docker-compose -f apiconvenios/docker-compose.yml up -d

# Construyendo servicio de proveedores
echo 'Compilando fuentes y generando artefactos de proveedores...'
mvn -f apiproveedores/pom.xml clean package -Dmaven.test.skip=true

docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=proveedores-service:latest --rm=true apiproveedores/
docker-compose -f apiproveedores/docker-compose.yml up -d

# Construyendo servicio de routing
echo 'Compilando fuentes y generando artefactos de routing...'
mvn -f apiintermediaterouting/pom.xml clean package -Dmaven.test.skip=true

docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=routing-service:latest --rm=true apiintermediaterouting/
docker-compose -f apiintermediaterouting/docker-compose.yml up -d

# Construyendo servicio de proxy sap
echo 'Compilando fuentes y generando artefactos de proxy SAP...'
mvn -f apiproxysap/pom.xml clean package -Dmaven.test.skip=true

docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=apiproxysap-service:latest --rm=true apiproxysap/
docker-compose -f apiproxysap/docker-compose.yml up -d

# Construyendo servicio de proxy sap
echo 'Compilando fuentes y generando artefactos de proxy SAP...'
mvn -f apimail/pom.xml clean package -Dmaven.test.skip=true

docker build --no-cache=true --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') --build-arg BUILD_VERSION=1.0-stable --tag=email-service:latest --rm=true apimail/
docker-compose -f apimail/docker-compose.yml up -d

# Eliminando imagenes residuales
echo 'Limpiando imagenes residuales...'
docker rmi $(docker images -f "dangling=true" -q)

dtf=$(date '+%d/%m/%Y %H:%M:%S')

echo 'Iniciado  a : ' $dti
echo 'Terminado a : ' $dtf