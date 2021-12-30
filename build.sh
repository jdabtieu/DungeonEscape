#!/bin/bash

SECONDS=0

echo "Starting up..."
rm -rf build
mkdir build

echo "Compiling..."
javac -source 1.8 -target 1.8 -g:none -sourcepath src -d build $(find src/* | grep .java)

echo "Building..."
jar cmvf META-INF/MANIFEST.MF DungeonEscape.jar -C build .

echo "Cleaning up..."
rm -rf build

duration=$SECONDS
echo "Build completed in $(($duration / 60)) minutes and $(($duration % 60)) seconds."