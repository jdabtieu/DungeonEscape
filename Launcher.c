#include <windows.h>
#include <shellapi.h>

int WINAPI WinMain (HINSTANCE _ua, HINSTANCE _ub, PSTR _uc, int _ud) {
    ShellExecute(NULL, "open", "DungeonEscape.jar", NULL, NULL, SW_SHOWNORMAL);
}
