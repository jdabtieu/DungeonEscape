// gcc Launcher.c icon.res launcher.res -o ../Launcher.exe -mwindows -s -O3

#include <windows.h>
#include <shellapi.h>

int WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow) {
  ShellExecute(NULL, "open", "DungeonEscape.jar", NULL, NULL, SW_MAXIMIZE);
  return 0;
}
