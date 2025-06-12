#!/bin/bash

if [ ! -f .env ]; then
  read -p "Enter DB username: " DB_USER
  read -p "Enter DB password: " DB_PASS
  echo "Generating a secure JWT Secret..."
  JWT_SECRET=$(openssl rand -hex 64) # Generate a random 64-byte hex string
  echo

  echo "DB_USER=$DB_USER" > .env
  echo "DB_PASS=$DB_PASS" >> .env
  echo "JWT_SECRET=$JWT_SECRET" >> .env

  echo ".env file created."
else
  echo ".env file already exists. Skipping creation."
fi

docker-compose -f docker-compose.aws.yml up -d