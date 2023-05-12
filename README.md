# GeoQuest Ktor API

This repository contains the source code for the GeoQuest API developed with Ktor. GeoQuest is an application that provides services related to geolocation and place search.

## Table of Contents

- [Features](#features)
- [Authors](#authors)
- [Configuration](#configuration)
- [Usage](#usage)
- [License](#license)


## Features

- The API utilizes the [Ktor](https://ktor.io/) framework, which is a framework for [Kotlin](https://kotlinlang.org/) for building connected applications – web applications, HTTP services, mobile and browser application.
- Also we are using the [Exposed](https://github.com/JetBrains/Exposed) framework to make the connection and the queries to our PosgreSQL database provided by Aiven, a cloud-hosted database to store and manage place-related information.
- The API is used by our [Android Application](https://gitlab.com/ivan.martinez.7e6/geoquest-mobile) and our [Web platform](https://gitlab.com/asier.barranco.7e6/geoquest-web) and make use of [Json Web Tokens](https://jwt.io/) method for authenticate 

## Authors

[Alejandro Arcas Leon](https://gitlab.com/Xalexx)

[Ivan Martinez Cañero](https://gitlab.com/ivan.martinez.7e6)

[Joel Garcia Galiano](https://gitlab.com/joel.garcia.7e6)


## Configuration

To install and set up the GeoQuest server on your local development environment, you just have to clone the repository:

```bash
git clone https://gitlab.com/Xalexx/geoquest-ktorapi.git
```

The API will start at `http://localhost:8080`.

## Usage

The API provides the following endpoints:

- `GET /user/{username}`: Get the user by their username.
- `GET /treasures`: Get a list of treasures.
- `POST /places`: Create a new place.
- `PUT /places/{id}`: Update an existing place.
- `DELETE /places/{id}`: Delete an existing place.

You can use tools like cURL or Postman to interact with the API.


## License

This project is distributed under the MIT license. Refer to the [LICENSE](LICENSE) file for more information.

