#!/bin/bash

rm -rdf gen_src
mkdir gen_src
thrift -out gen_src -strict --gen java handbook.thrift
