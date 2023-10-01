# This first lab of course "Technology of web services" #

### How to launch ###

## Standalone mode ##

1) Go to the module `standalone` and execute next command `mvn clean jaxws:wsimport` to generate classes for client
2) Go to the docker folder in `webapp` and run db
3) Run `standalone` module
4) Run `standalone-client` module

## Webapp mode ##
1) Go to the module `standalone` and execute next command `mvn clean jaxws:wsimport` to generate classes for client
2) Go to the docker folder and run docker compose
3) Change URL in `Main.kt` in `standalone-client` module
4) Run `standalone-client` module
