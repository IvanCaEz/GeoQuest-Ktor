# GeoQuest Ktor API

This repository contains the source code for the GeoQuest API developed with Ktor. GeoQuest is an application that provides services related to geolocation and place search.

## Table of Contents

- [Features](#features)
- [Authors](#authors)
- [Configuration](#configuration)
- [Endpoints](#endpoins)
- [License](#license)


## Features

- The API utilizes the [Ktor](https://ktor.io/) framework, which is a framework for [Kotlin](https://kotlinlang.org/) for building connected applications – web applications, HTTP services, mobile and browser application.
- Also we are using the [Exposed](https://github.com/JetBrains/Exposed) framework to make the connection and the queries to our PosgreSQL database provided by Aiven, a cloud-hosted database to store and manage place-related information.
- The API is used by our [Android Application](https://gitlab.com/ivan.martinez.7e6/geoquest-mobile) and our [Web platform](https://gitlab.com/asier.barranco.7e6/geoquest-web) and make use of [Json Web Tokens](https://jwt.io/) method for authenticate 

## Authors

[Alejandro Arcas Leon](https://gitlab.com/Xalexx)

[Martí Gustamante Clavell](https://gitlab.com/marti.gustamante.7e6)

[Ivan Martinez Cañero](https://gitlab.com/ivan.martinez.7e6)

[Asier Barranco Barbudo](https://gitlab.com/asier.barranco.7e6)

[Joel Garcia Galiano](https://gitlab.com/joel.garcia.7e6)

[Raul Argemi](https://gitlab.com/raul.argemi.7e6)


## Configuration

To install and set up the GeoQuest server on your local development environment, you just have to clone the repository:

```bash
git clone https://gitlab.com/Xalexx/geoquest-ktorapi.git
```

The API will start at `http://localhost:8080`.

## Endpoints

We have used swagger and openapi for documentating our endpoints, you can see them at: 
` /swagger `
 or
`/openapi`

## License

This project is distributed under the MIT license. Refer to the [LICENSE](LICENSE) file for more information.

