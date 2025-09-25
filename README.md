# My Books

Aplikacja pozwala (po rejestracji i zalogowaniu) na wyszukiwanie książek z GoogleBooks 
oraz na dodawanie ich do swojej listy, usuwanie lub oznaczanie jako przeczytane.  

## Działająca aplikacja

https://quizmaster.tigerstel.xyz/

## Instalacja

Aby łatwo wdrożyć aplikację, należy mieć Docker i Docker Compose na swoim komputerze.
Po sklonowaniu repozytorium przejdź do katalogu projektu i uruchom następujące polecenie:

```bash
./deploy.sh
```

Utworzy to plik „.env” (i prosi o wymagane zmienne środowiskowe oraz wygeneruje wartość Jwt Secret)
oraz uruchomi aplikację za pomocą Docker Compose.
Pobrane zostaną wszystkie obrazy dockerowe, projekt zostanie zbudowany i uruchomiony. Baza danych zostanie wygenerowana.

Jeśli chcesz, możesz ręcznie utworzyć plik „.env” z wymaganymi zmiennymi środowiskowymi
a także uruchomić aplikację bez Dockera np. z Gradle (bootRun) - backend i npm - frontend
(wymaga to jednak zainstalowania Javy, Gradle i Node.js).

## Zmienne środowiskowe

- DB_USER=
- DB_PASS=
- JWT_SECRET=

## Zastosowane Technologie

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Docker, Docker Compose
- React, TypeScript
- Liquibase
- Gradle
- Jupiter, Mockito, AssertJ
- JWT

# My Books

My Books is a web application that allows users to manage their book collections, including adding, editing, and deleting books. 

## Working deployed app

https://quizmaster.tigerstel.xyz/

## Installation

To deploy the application easily you have installed Docker and Docker Compose on your machine.
After cloning the repository, navigate to the project directory and run the following commands:

```bash
./deploy.sh
```

It creates '.env' file (and asks for the required environment variables and generates Jwt Secret value) 
and starts the application using Docker Compose.
If you want you can create the '.env' file manually with the required environment variables.

## Environment Variables

- DB_USER=
- DB_PASS=
- JWT_SECRET=