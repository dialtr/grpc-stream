#!/bin/bash
java -jar client/target/test-client.jar \
	--hostname 192.168.1.165              \
	--port 7171                           \
 	--query eventdriven                   \
	--threads 8                           \
	--count 1000

