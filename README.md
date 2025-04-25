# Proyecto Compose Multiplatform – App de prueba (Amazon & WebViews)

Este proyecto es una práctica usando **Compose Multiplatform** junto con llamadas a una API de prueba (https://jsonplaceholder.typicode.com/) y navegación web usando la librería de KevinZou (https://github.com/KevinnZou/compose-webview-multiplatform/tree/main).

---
This is a Kotlin Multiplatform project targeting Android, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
