# cluster
Permite crear un cluster de diferentes servers.
Para correrlo: compilarlo, y correr:
  java -jar cluster.jar clusters=api:20,frontEnd:10,static:5
  
  Eso crea 20 servers de api, 10 de frontEnd, y 5 de static
  
  Para acceder esos servers:
  curl -v localhost:9000/api/process ---> accede al servidor de api del puerto 9000
  curl -v localhost:9100/frontEnd/process ---> accede al servidor de frontEnd del puerto 9100
  curl -v localhost:9200/static/process ---> accede al servidor de frontEnd del puerto 9200
  
  Si se hace el request al puerto equivocado, devuelve 404
  Ejemplo:
      curl -v localhost:9200/api/process ---> Devuelve 404 Not Found porque el puerto 9200 es de un server de static
  
Se levantan hasta un máximo de 30 servers por tipo, y los puertos son: 9000 hasta 9099 para el primer tipo de servers (en el ejmplo, api), 9100 hasta 9199 para el segundo tipo (en ejemplo, frontEnd) y así sucesivamente (o sea, el puerto inicial de cada cluster se va incrementando de a 100).

Respuesta: la respuest tiene la forma:
  {"delay":144} ---> el delay en la respuesta corresponde a los dos últimos dígitos de cada puerto elevados al cuadrado.
  Por ejemplo, un request al puerto 9020, va a demorar 20^2 = 400 ms
