#!/bin/bash
thrift --gen py -out ../ message.thrift
thrift --gen java -out ././../../message-java-thrift-service/src/main/java/ message.thrift