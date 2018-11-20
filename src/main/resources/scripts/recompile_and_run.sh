#!/bin/bash

SLEEP_TIME=0.2 # DO NOT CHANGE THIS!

./recompile.sh

sleep $SLEEP_TIME

./run.sh
