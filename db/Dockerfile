FROM postgres:18

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=ems_db

COPY ems_db_dump.sql /docker-entrypoint-initdb.d/