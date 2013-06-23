#!/bin/bash

echo '[INFO] Start create distribution'
rm ./SelectionCommittee.tar.gz
rm ./SelectionCommittee.zip

echo '[INFO] Building application...'
mvn clean package

cd ./target
tar -czf SelectionCommittee.tar.gz bin lib
zip -r SelectionCommittee.zip bin lib

cd ./..
mv ./target/SelectionCommittee.tar.gz ./
mv ./target/SelectionCommittee.zip ./

echo '[INFO] Finish create distribution'
