# grpc-stream

## About

The impetus for this project was that I was unable to find a good example of
how to use GRPC streaming properly. The examples that I did find were naive
in the sense that they did not show how to use the event-driven features of
the framework.

The goal here is to provide a few examples of each flavor of GRPC streaming
that are well-documented. At the present time, only unidirectional streaming
from the server to the client is demonstrated.

## Requirements

* Linux 
* Java 11 (or later, but this has not been tested)
* Maven

## Getting the source

    git clone git@github.com:dialtr/grpc-stream.git

## Building

As mentioned in the requirements section, this project uses Maven. To build:

    mvn package

The build process will cause the protos to be generated as well as two fat
JAR files (one for the server and one for the client, respectively.) 

## Running

There are several shell scripts at the root of the project for running the
server and client:

    test-server.sh         # Run the server
    test-client-naive.sh   # Run the client in "naive" mode
    test-client-smart.sh   # Run the client in "smart" mode

The *naive* client script tells the server to use a naive method for
streaming that is often shown in examples, but which causes problems. The
problem is that the server sends data to the client without waiting for
readiness, which causes data to buffer excessively on the server side if
the client isn't keeping up.

The *smart* client script tells the server to use an event driven approach
so that data is only sent when the client is ready.

## Contact

Tom R. Dial <dialtr@gmail.com>

