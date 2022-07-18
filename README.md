# Selenium Scalable Parallel Test Execution

![Selenium Java](https://img.shields.io/maven-central/v/org.seleniumhq.selenium/selenium-java?color=43B02A&label=selenium&logo=selenium&style=for-the-badge)
![Kubernetes](https://img.shields.io/github/v/release/kubernetes/kubernetes?color=%23326ce5&label=kubernetes&logo=kubernetes&style=for-the-badge)
![Docker Engine](https://img.shields.io/github/v/release/docker/docker?color=0db7ed&label=docker&logo=docker&style=for-the-badge)

Scalable parallel automated tests with Kubernetes cluster, Docker containers and Selenium 4

## Prerequisites

Make sure you have installed and configured the environment variables all the following prerequisites on your
development machine:

| OS      | JDK                                | Maven                 | Docker                                        | Kubernetes                    | Helm                 |
|---------|------------------------------------|-----------------------|-----------------------------------------------|-------------------------------|----------------------|
| Windows | `scoop install java/temurin17-jdk` | `scoop install maven` | `winget install -e --id Docker.DockerDesktop` | `scoop install kubectl`       | `scoop install helm` |
| macOS   | `brew install --cask temurin`      | `brew install maven`  | `brew install docker`                         | `brew install kubernetes-cli` | `brew install helm`  |

### Deploying to Kubernetes

- Add docker-selenium helm repository.

```kubernetes helm
   helm repo add docker-selenium https://www.selenium.dev/docker-selenium
```

- Install grid in the fully distributed mode (Router, Distributor, EventBus, SessionMap and SessionQueue components
  separated).

```kubernetes helm
   helm install selenium-grid docker-selenium/selenium-grid --set isolateComponents=true
```

- Upgrade helm chart if a new version released.

```kubernetes helm
   helm upgrade selenium-grid docker-selenium/selenium-grid
```

## Executing the Tests

After deploying the Selenium Grid using the .yaml file inside the project, execute the test with the following command:

```shell
mvn clean test -Dbrowser='browserName'
```

You can specify which browser to use by using one of the following on the command line:

- `-Dbrowser=chrome`
- `-Dbrowser=firefox`
- `-Dbrowser=edge`