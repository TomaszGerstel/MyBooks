# My Books

My Books is a web application that allows users to manage their book collections, including adding, editing, and deleting books. 

## Installation

To deploy the application easily you have installed Docker and Docker Compose on your machine.
After cloning the repository, navigate to the project directory and run the following commands:

```bash
./deploy.sh
```

It creates '.env' file (and asks for the required environment variables) and starts the application using Docker Compose.
If you want you can create the '.env' file manually with the required environment variables.

## Environment Variables

- DB_USER=
- DB_PASS=
- JWT_SECRET=