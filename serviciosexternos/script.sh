#!/bin/bash

echo ''
echo '+------------------------------------------------------------------'
echo '|     MODELOS Y VALIDACION UNIVERSIDAD JAVERIANA                   '
echo '|     CREANDO SERVICIOS EXTERNOS DEL BANCO                         '
echo '+------------------------------------------------------------------'
echo ''

BASE_DIR=$(pwd)
BASE_APP='serviciosexternos'

DIRECTORIO=$BASE_DIR/$BASE_APP

echo 'Directorio base de la aplicacion... ' $DIRECTORIO

echo 'Creando servicio de facturacion...'
docker build -t factura-service:1.0 --network backend $DIRECTORIO/factura/NewService
docker run -d -p 9090:8080 --name factura-service factura-service:1.0

echo 'Creando servicio de gas...'
docker-compose -f $DIRECTORIO/gas-service-soap/docker-compose.yml up --build -d

echo 'Creando servicio de agua...'
docker build -t agua-service:1.0 --network backend $DIRECTORIO/water-service-rest
docker run -d -p 9091:8080 --name agua-service agua-service:1.0