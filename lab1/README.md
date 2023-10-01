# This first lab of course "Technology of web services" #

### How to launch ###

## Standalone mode ##

1) Go to the docker folder in `webapp` and run db
2) Run `standalone` module
3) Go to the module `standalone-client` and execute next command `mvn clean jaxws:wsimport` to generate classes for client
4) Run `standalone-client` module

## Webapp mode ##

1) Go to the docker folder and run docker compose
2) Change URL in `Main.kt` in `standalone-client` module
3) Run `standalone-client` module
