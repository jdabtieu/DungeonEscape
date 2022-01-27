@echo off

echo It is strongly recommended to use the build.sh script instead.
echo This script can only compile, and cannot package or build.
echo If you would still like to continue, press enter
pause

echo Starting up...
rmdir /S /Q build 2>nul
mkdir build

echo Compiling...
javac -encoding UTF-8 -source 1.8 -target 1.8 -g:none -d build -cp src src/com/jdabtieu/DungeonEscape/Main.java

echo Building...
jar cmvf META-INF/MANIFEST.MF DungeonEscape.jar -C build .

echo Cleaning up...
rmdir /S /Q build 2>nul

echo Build completed.

pause

java -jar DungeonEscape.jar