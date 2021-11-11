# Selenium Scalable Parallel Test Execution

![Selenium Java](https://img.shields.io/maven-central/v/org.seleniumhq.selenium/selenium-java?color=43B02A&label=selenium&logo=selenium&style=for-the-badge)
![Kubernetes](https://img.shields.io/github/v/release/kubernetes/kubernetes?color=%23326ce5&label=kubernetes&logo=kubernetes&style=for-the-badge)
![Docker Engine](https://img.shields.io/github/v/release/docker/docker?color=0db7ed&label=docker&logo=docker&style=for-the-badge)

Scalable parallel automated tests with Kubernetes cluster, Docker containers and Selenium

## Requirements

- This project requires [Java 11 JDK](https://adoptopenjdk.net/).
- Install [Docker](https://docs.docker.com/engine/install) engine.
- Install [Kubectl](https://kubernetes.io/docs/tasks/tools/#kubectl).
- Install [Minikube](https://kubernetes.io/docs/tasks/tools/#minikube) to run Kubernetes as locally.
- Specify any VM driver appropriate for your OS. Review the doc for driver
  list : [https://minikube.sigs.k8s.io/docs/drivers/](https://minikube.sigs.k8s.io/docs/drivers/)

### Deploying to Kubernetes

```shell
# Configure the vm-driver
$ minikube config set vm-driver '<vm-driver-name>'

# Start minikube with configured CPU & memory
$ minikube start --cpus 4 --memory 8192

# Deploy all the grid components to kubernetes
$ kubectl apply -f deploy.yml

# Expose the router
$ kubectl expose deployment selenium-router-deployment --type=NodePort --port=4444

# Get the router URL to access the grid from outside K8s cluster
$ minikube service selenium-router-deployment --url

## To access the dashboard
$ minikube dashboard
```

## Commands

After deploying the Selenium Grid using the .yaml file inside the project,
execute the test with the following command:

```
mvn clean test -Dbrowser='browserName'
```

You can specify which browser to use by using one of the following
on the command line:

- `-Dbrowser=chrome`
- `-Dbrowser=firefox`
