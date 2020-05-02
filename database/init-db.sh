#!/bin/bash

export PGCONNECT_TIMEOUT=1
export PGPASSWORD='Password1!'

function pingPg() {
    psql -U postgres -h 127.0.0.1 -c 'select 1;' > /dev/null
}

function waitForPg() {
    if ! pingPg; then
        echo "Waiting for PostgreSQL to start..."
        while ! pingPg; do
           sleep 1
        done
        echo "PostgreSQL is ready."
    fi
}

function initPg() {
    psql -U postgres -h 127.0.0.1 -t << eof
        ALTER USER postgres WITH PASSWORD 'Password1!';
        CREATE USER swami_user WITH PASSWORD 'Password1!';
        CREATE DATABASE swami;
        GRANT ALL PRIVILEGES ON DATABASE swami_user to swami;
eof
}

waitForPg
initPg
