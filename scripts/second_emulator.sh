#! /bin/bash
emulator -list-avds
emulator -avd Pixel_8_Pro
adb install /home/usuario/Descargas/upload/composeApp-release.apk
npm run test-all