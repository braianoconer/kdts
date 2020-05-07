# Dummy Translation Service (with Kotlin) - KDTS

This project is intended to be used as a PoC for Observability principles. If you want to read more about 
the concept of observability in distributed systems see the [References] section below.

## DTS - High Level Architecture

The system is composed of 5 services devoted to wonderful task of translating completely useless and irrelevant English sentences.

![DTS Architecture](/images/DTS-Arch.png)

## How to run the project using docker-compose

Install [Docker](https://www.docker.com/) v19.0+ in your machine and simply run 

    docker-compose up -d
    
Make sure that you have enough resources, otherwise services like 'elastic' or 'kafka' won't be able to start

![Docker minimun resources](/images/dockerMinResources.png)
     
## How to recreate the docker images for each of the services   

You can use the convenience ``buildAll.sh`` script to rebuild all the images


## How to run the project in Kubernetes

Refer to the [k8s section](k8s/README.md)

## Using the Dummy Translator Service

Once the system is up and all its services are up and running you can trigger the translation process by sending a GET request to 
the English initiator service. For example:

    http://localhost:8091/start?size=10 
   
Will trigger the service to send batches of 10 messages (in English) to the __english__ kafka topic every second

You can also stop the publishing using an equivalent GET request:

    http://localhost:8091/stop    
    
## Observing the system

Once the DTS system is up and running you can use the list of URLs below to connect to each of the services providing insights about how the system is behaving:

Kibana: http://localhost:5601/

Prometheus UI: http://localhost:9090/

Grafana: http://localhost:3000/

Zipkin UI: http://localhost:9411/

The ports for each of these services can be modified in the ``docker-compose.yml`` file

Also, you can optionally start up Kafka console consumers as well in case you want to see the flow of messages coming in each Kafka topic.
This can be done in two ways depending on whether you have a local installation of Kafka or prefer to use the dockerized version:

1. Local Kafka
    
    
    ${KAFKA_HOME}/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic $TOPIC

So, for example, to consume messages from the Spanish topic:

    ${KAFKA_HOME}/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic spanish    

2. From the dockerized Kafka

    docker run -it --rm --network="dts_observability" --link observability-poc_kafka_1 bitnami/kafka:2 /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka:29092 --topic spanish    
    
## References


* [The three pillars of Observability](https://www.oreilly.com/library/view/distributed-systems-observability/9781492033431/ch04.html)
  Distributed Systems Observability by Cindy Sridharan, O'Reilly
  
### Events logs
* [Elastic Stack](https://www.elastic.co/elastic-stack)


### Metrics
* [Prometheus IO](https://prometheus.io/) 

* [Micrometer.io](https://micrometer.io/docs)


### Distributed Tracing
* [The Open Tracing project](https://opentracing.io/)

* [Zipkin IO](https://zipkin.io/)

* [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
