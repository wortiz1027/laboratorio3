| <img src="documentacion/images/logo_pug.png" width="150px" height="200px"> | <h1> PONTIFICIA UNIVERSIDAD JAVERIANA </h1> |
| :--: |  :--: |

## TALLE 3 MODELOs Y VALIDACIÓN

### EQUIPO 5 
El equipo 5 está conformado por:
  - Jhon Edward Celemin Florez  
  - Eduardo José Franco Rivera
  - Wilman Alberto Ortiz Navarro
  - Brian Camilo Suarez Botia  

### TABLA DE CONTENIDO 

1. [Descripcion del problema](#DESC-PROBLEMA)
2. [Arquitectura de la Solucion](#ARQ-SOL)

   2.1. [Justificación de la Arquitectura](#ARQ-SOL)
        - [Microservicios](#ARQ-MICRO)
        
        - [Docker](#DOCKER-MICRO)
        
        - [Api Gateway](#GATEWAY-MICRO)
        
        - [RabbitMQ](#RABBIT-MICRO)
        
        - [Intermediate Routing](#ROUTING-MICRO)
3. [Inventario de Microservicios](#INV-MICRO)
4. [Documentacion de Microservicios](#DOC-MICRO)

#### 1. Decripcion del problema <a name="DESC-PROBLEMA"></a>

El Banco ABC está realizando varios proyectos de actualización tecnológica los cuales le permiten ofrecer sus productos financieros de manera más ágil y de ésta forma responder a nuevas necesidades del mercado. 

El Banco acaba de firmar una alianza estratégica con diferentes proveedores de servicios públicos (Agua, Gas, Luz, Telefonía) o también llamados convenios, para permitir a los clientes del banco a través de los diferentes canales de servicio (Cajeros Automáticos, Cajero de Oficina, Teléfono, Portal Web, Aplicación Móvil) permitir el pago de los mismos. 

El proceso de vista al usuario se puede definir de la siguiente manera a través de las siguientes tareas:

![alt text](documentacion/images/proceso_pagos.png "Proceso de pagos")

Cada uno de los proveedores de servicios públicos ofrece los mecanismos de interacción tecnológica necesarios para que el Banco pueda ejecutar las acciones de pago.

Cada proveedor utiliza tecnologías diferentes para ofrecer sus servicios.

> ***El banco cuenta con un sistema SAP que ofrece varios servicios Web SOAP con los cuales se realiza la actual validación del usuario. La verificación de saldos en las cuentas se realiza a través de una interface de sockets a un aplicativo Java que se encuentra en un sistema AS/400.***

> ***El Banco ABC quiere tener la posibilidad de adicionar nuevos convenios con otros proveedores de servicios de manera ágil, o incluso la posibilidad de terminar/eliminar los convenios existentes sin que esto represente indisponibilidad del servicio.***

Se llegó a un acuerdo de las capacidades/primitivas básicas que se deben soportar para cada convenio:

    • Consulta de saldo a Pagar
    • Pago del Servicio
    • Compensación de pago (Opcional)

Sin embargo, el proveedor del servicio de Gas no soporta actualmente la capacidad de ‘Compensación de pago’. Puede que este proveedor la soporte en el futuro.

Para este tipo de situaciones, donde los proveedores no soportan la última opción, se debe generar un mensaje por correo electrónico dirigido al área de soporte de incidentes de Banco, para que ellos se hagan cargo.

Principalmente el banco necesita un conjunto de servicios que representen sus necesidades internas de negocio, lo cual les permite desacoplar los servicios de los proveedores y así no depender de sus detalles.

La definición de los servicios se encuentra [aqui](https://github.com/germansua/UJaveriana-AES-ModVal/tree/master/modval/workshops "Repositorio github servicios externos del banco")

#### 2. Arquitectura de solución <a name="ARQ-SOL"></a>
  
##### 2.1. Justificación de la Arquitectura <a name="ARQ_JUSTIFICACION"></a>

##### MicroServicios <a name="ARQ-MICRO"></a>

> *Se utilizo para que el equipo trabajara en pequeños componentes y así poder terminar en el tiempo estimado para el desarrollo. Con esto las responsabilidades de cada integrante del equipo están mas definidas, ya que se definió componentes de la aplicación para cada uno. Esto permitió que cada uno se preocupara por cada microservicio y así no tener dependencias con los demás desarrollos.**

##### Docker <a name="DOCKER-MICRO"></a>

> *Se utilizo para simplificar el proceso de desarrollo y despliegue de la aplicación, permitido el desarrollo de una manera más rápida y ágil, ya que cada miembro del equipo no tenia que depender de alguien en especifico para que tuviera las maquinas arriba para las pruebas.** 

##### Api Gateway <a name="GATEWAY-MICRO"></a>

> *Se utilizo para apoyar la arquitectura de microservicios, se definió para proporcionar un punto central para el consumo de las diferentes apis dentro de la aplicación, va a ser el responsable de atender las diferentes solicitudes que lleguen a la aplicación y va a redireccionar el trafico a los diferentes servicios definidos.*


##### RabbitMQ <a name="RABBIT-MICRO"></a>

> *Se utilizo para definir las colas que van a almacenar los mensajes que envían las apis de los diferentes servicios, estos van a estar esperando hasta que lleguen las peticiones de consumo. Permitiendo que los mensajes que envían las apis, se enruten al consumidor correcto. Esto ayudara a la integración de los diferentes componentes, sistemas internos y externos.*

##### Intermediate Routing <a name="ROUTING-MICRO"></a>

> *Se utilizo para identificar los diferentes servicios de destino y poderlos enrutar dinámicamente, se basa en una base de datos la cual contiene todos los endpoint de los servicios de la aplicación, actúa como una regla de negocio la cual por el nombre del servicio identifica el servicio de destino, luego, por medio del rabbitMQ o Api Gateway se enruta hacia su destino.*

#### 3. Inventario de Microervicios <a name="INV-MICRO"></a>

| Código | Versión | Nombre | Descripción | Endpoint |
|---|:-:|:-:|:-:|:-:|
| API_0001 | v1.0 | Consulta Saldos | Permite validar si hay saldo en la cuenta del cliente | http://localhost:52001/apisaldos/api/v1.0/saldos |
| API_0002 | v1.0 | Consulta Usuario SAP | Permite la validación de los usuarios | http://localhost:55725/sap/SapService |
| API_0003 | v1.0 | Consulta Servicio Facturas | Muestra los datos del servicio de facturas | http://localhost:9090/api/domiciliacion/factura?fid= |
| API_0004 | v1.0 | Consulta Servicio Agua | Muestra los datos del servicio de agua | http://localhost:9091/servicios/pagos/v1/payments/1 |
| API_0005 | v1.0 | Consulta Servicio Gas | Muestra los datos del servicio de gas | http://localhost:8080/gas-service/PagosService?wsdl |
| API_0006 | v1.0 | Api Convenios | Api con el CRUD para los proveedores | http://localhost:8060/apiproveedores/api/v1.0/proveedores |
| API_0007 | v1.0 | Api Intermediate Routing | Api para enrutar la invocación de servicios | http://localhost:8070/apirouting/api/v1.0/routing/{servicio}/servicio |
| API_0008 | v1.0 | Api Mail | Api para el envio de correos | http://localhost:8040/apimail/api/v1.0/email/Gas |
| API_0009 | v1.0 | Api gateway | Enrutamiento de servicios | http://localhost:8050/apiproxy/api/v1.0/proveedores |
| API_0010 | v1.0 | Api Proveedores | Api con el CRUD para los proveedores | http://localhost:8060/apiproveedores/api/v1.0/proveedores |

#### 4. Documentación de Microservicios <a name="DOC-MICRO"></a>