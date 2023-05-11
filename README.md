# GeoQuest Ktor API

This repository contains the source code for the GeoQuest API developed with Ktor. GeoQuest is an application that provides services related to geolocation and place search.

## Table of Contents

- [Features](#features)
- [Authors](#authors)
- [Configuration](#configuration)
- [Usage](#usage)
- [License](#license)


## Features

- The API utilizes the Ktor framework, which is a Kotlin implementation of the web and server application design pattern.
- It uses a cloud-hosted database provided by Aiven to store and manage place-related information.
- The API is exposed to be accessible from any external client or application.

## Authors

[Alejandro Arcas Leon](https://gitlab.com/Xalexx)

[Ivan Martinez Cañero](https://gitlab.com/ivan.martinez.7e6)

[Joel Garcia Galiano](https://gitlab.com/joel.garcia.7e6)


## Configuration

To install and set up GeoQuest Mobile on your local development environment, you just hav to clone the repository:

```bash
git clone https://gitlab.com/Xalexx/geoquest-ktorapi.git
```

The API will start at `http://localhost:8080`.

## Usage

The API provides the following endpoints:

- `GET /places`: Get all the places stored in the database.
- `GET /places/{id}`: Get a specific place by its ID.
- `POST /places`: Create a new place.
- `PUT /places/{id}`: Update an existing place.
- `DELETE /places/{id}`: Delete an existing place.

You can use tools like cURL or Postman to interact with the API.


## License

This project is distributed under the MIT license. Refer to the [LICENSE](LICENSE) file for more information.

