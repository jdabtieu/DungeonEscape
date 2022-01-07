#!/bin/bash

# Help menu
if [ "$1" == "help" ]; then
  echo "Run './build.sh' to compile and build to jar only"
  echo "Run './build.sh package' to compile and build to jar, and package into a SFX zip"
  echo "Run './build.sh launcher' to compile and build the launcher"
  echo "Run './build.sh help' to view this help menu"
  exit 0
fi

# Launcher
if [ "$1" == "launcher" ]; then
  SECONDS=0

  echo "Building..."
  cd launcher-build
  cmd //C 'windres icon.rc -O coff -o icon.res'
  cmd //C 'windres launcher.rc -O coff -o launcher.res'
  gcc Launcher.c icon.res launcher.res -o ../Launcher.exe -mwindows -s -O3
  rm icon.res
  rm launcher.res
  cd ..

  duration=$SECONDS
  echo "Build completed in $(($duration / 60)) minutes and $(($duration % 60)) seconds."
  exit 0
fi

# Compile and Build
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

# Package
if [ "$1" == "package" ]; then
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
fi