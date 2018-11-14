#!/bin/bash

TARGET_DIR="target"

if [ ! -d ${TARGET_DIR} ]; then
    echo "${TARGET_DIR} is not a directory or does not exist. Aborting..."

    exit 1
else
    echo "Removing every file in target/ with 'rm -rf target/*' ..."
    rm -rf ${TARGET_DIR}/*

    if [ -z "$(ls -A ${TARGET_DIR})" ]; then
        echo "Done."
    else
        echo "Operation failed. ${TARGET_DIR} is not empty after attempting to delete its content."

        exit 1
    fi
fi