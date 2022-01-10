                                 DungeonEscape
                                  Jonathan Wu

Included in this folder is the source code for the DungeonEscape game, assets,
and a precompiled version of the game.

Playing the Game
    On any platform, with Java 8 or above (Oracle preferred), running the
    command `java -jar DungeonEscape.jar` will launch the game. On Windows, the
    game can also be launched by double clicking the Launcher.exe file.
    
    System Requirements (Required):
      Java 8+
      Dual-Core CPU, at least 2.6 GHz
      2GB RAM
      1024x768 monitor resolution
    
    System Requirements (Recommended):
      Java 8+ (Oracle)
      Quad-Core CPU, at least 2.6 GHz
      4GB RAM
      1280x1024 monitor resolution
      Dedicated GPU



                                Developer Notes

Source code:
    src/              contains all Java code
    launcher-build/   contains C & rc files for the launcher
    META-INF/         contains the MANIFEST.MF file for the runnable JAR
    
Assets:
    assets/           general assets
    assets/font/      local copies of fonts used, in case the font is not
                      available on the system
    assets/music/     music used in the game
    assets/stage/     text files describing the layout of each stage
    assets/weapon/    images of each weapon
    
Build tools:
    The build.sh script is used to compile and build the game. It runs on Bash,
    whether through a Linux Bash shell, WSL, or Git Bash for Windows. It will
    not run on cmd or Powershell. If you do not have access to a bash shell, use
    the precompiled version of the game.
    
    To simply compile and build to a JAR file, the bash shell is all that's
    required, and can be done by running the following in a bash shell:
      ./build.sh
      
    To build as above, and package the game into a SFX archive for distribution,
    7z (the 7-zip command line utility) must be present in the PATH. This is
    commonly done by installing MinGW.
      ./build.sh package
    
    To build the Windows launcher, gcc and windres must be available in the
    PATH. This is commonly done by installing MinGW.
      ./build.sh launcher