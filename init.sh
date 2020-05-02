#!/bin/bash

dt=$(date '+%d/%m/%Y %H:%M:%S')

echo '+------------------------------------------------------------------'
echo '|     MODELOS Y VALIDACION UNIVERSIDAD JAVERIANA                   '
echo '|     FECHA      :  ' $dt
echo '|     AUTORES    : EQUIPO 5                                        '
echo '|     DESCRIPCION: CREACION E INICIO DE CONTENEDORES DEL BANCO ABC '
echo '+------------------------------------------------------------------'

# Eliminando redes en caso de que existan
echo 'Eliminando redes...'
docker network rm $(docker network ls | grep "backend" | awk '/ / { print $1 }')

# Creando la red comun para todos los contenedores
echo 'Creando red backend'
docker network create --driver bridge backend

# Script para crear los servicios internos del banco
sh serviciosinternos/script.sh