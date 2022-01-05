#!/bin/bash

SECONDS=0

echo "Starting up..."
rm -rf build
mkdir build

echo "Compiling..."
javac -encoding UTF-8 -source 1.8 -target 1.8 -g:none -sourcepath src -d build $(find src/* | grep .java)

echo "Building..."
jar cmvf META-INF/MANIFEST.MF DungeonEscape.jar -C build .

echo "Cleaning up..."
rm -rf build

duration=$SECONDS
echo "Build completed in $(($duration / 60)) minutes and $(($duration % 60)) seconds."

SECONDS=0

echo "Packaging..."
rm -rf DungeonEscape
rm -f DungeonEscape.exe
mkdir DungeonEscape
cp -r DungeonEscape.jar Launcher.exe assets DungeonEscape
7z a DungeonEscape.exe -sfx DungeonEscape
rm -rf DungeonEscape

duration=$SECONDS
echo "Packaging completed in $(($duration / 60)) minutes and $(($duration % 60)) seconds."