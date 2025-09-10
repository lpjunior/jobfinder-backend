#!/bin/sh
set -eu

if [ -n "${DATABASE_URL:-}" ] && [ -z "${SPRING_DATASOURCE_URL:-}" ]; then
  proto_removed="${DATABASE_URL#postgres://}"
  creds="${proto_removed%@*}"
  hostportdb="${proto_removed#*@}"

  user="${creds%%:*}"
  pass="${creds#*:}"
  hostport="${hostportdb%/*}"
  db="${hostportdb#*/}"

  export SPRING_DATASOURCE_URL="jdbc:postgresql://${hostport}/${db}"
  export SPRING_DATASOURCE_USERNAME="${user}"
  export SPRING_DATASOURCE_PASSWORD="${pass}"
fi

exec java -jar /app/app.jar
