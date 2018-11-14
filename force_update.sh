#!/bin/bash

echo "Executing 'git stash' ..."
git stash
echo "Done."
echo "Executing 'git pull -f' ..."
git pull -f
echo "Done."