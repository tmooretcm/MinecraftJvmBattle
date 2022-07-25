# Minecraft-OpenJ9-Project

Minecraft OpenJ9 Project

## Goals

The goal of this project is to measure the performance differences in Minecraft between the default bundled HotSpot JVM and the OpenJ9 JVM. Leveraging Prometheus, Grafana, and Docker, we will measure performance differences between containerized Minecraft servers using the different JVMs in terms of memory footprint, CPU usage, server start/response time, and FPS. 

## Getting Started

#### Prerequisites

Make sure that [Prometheus](https://prometheus.io/docs/prometheus/latest/installation/), [Grafana](https://grafana.com/docs/grafana/latest/setup-grafana/installation/), and [Docker](https://docs.docker.com/get-docker/) are all installed. Once done, clone this repo.

After this, you will have to expose Docker for Prometheus metrics scraping. Follow only the **Configure Docker** section of this [guide](https://docs.docker.com/config/daemon/prometheus/).

Next, you will have to include this repo for file sharing with Docker. Navigate to Settings > Resources > File Sharing and add this repository's directory (it will add all subdirectories) to enable proper usage. Example path added to file sharing: /Users/tmooretcm/Minecraft-OpenJ9-Project.

#### Running the monitor with Docker

1. Access the /mc/mc-monitor/examples/mc-monitor-prom/ directory.
2. Run the following command to start the monitor composition:
```
$ docker-compose up -d
```
3. To stop the monitor, run the following command:
```
$ docker-compose down
```
#### Accessing Grafana

1. Open a browser to http://localhost:3000 and log in initially with the username "admin" and password "admin". You will then be prompted to create a new password for the admin user.
2. A dashboard called "MC Monitor" is provisioned and can be accessed in the dashboards section. That dashboard uses the Prometheus datasource that was provisioned.

## Displaying Metrics

With the monitor running and the MC Monitor dashboard opened on Grafana, there should be four default panels displayed. 
1. Click on the dropdown next to either *Minecraft Service - CPU Usage* or *Minecraft Service - Memory Usage*, and hit *Edit* in order to change the metrics displayed.
2. Under *Metrics browser*, you can 1. select a metric to monitor, 2. select which label the metric will measure - **unless there's not a default option, hit ID and scroll to the docker process corresponding to _itzg_minecraft_server_**.

## Testing
Currently, the tests can be run using Eclipse after importing the project. You can right-click the UnitTesting.java class -> Run As -> JUnit to run the tests and view the output.
