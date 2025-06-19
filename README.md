
<h1 align="center"><img src="https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExN3E4N3RzcjRxc3VvOWlzeG5qOHlqNHlqMXBkeGIwcjB4YnZmdzZiOCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/fxTDiduVAhCgnNLaG2/giphy.gif" width="60"/>Proyecto Mock Multiplatform<img src="https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExN3E4N3RzcjRxc3VvOWlzeG5qOHlqNHlqMXBkeGIwcjB4YnZmdzZiOCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/fxTDiduVAhCgnNLaG2/giphy.gif" width="60"></h1>

Este proyecto es una aplicación mock desarrollada con **Compose Multiplatform**. Fue creada con el objetivo de simular el comportamiento de una app real, incorporando pruebas automatizadas, flujos de trabajo, firma de la aplicación, principios de arquitectura, entre otros aspectos clave.

## Reflexiones
Este proyecto lo desarrollé durante mis 3 meses de prácticas en una empresa a la que le tengo mucho cariño. Me permitió descubrir muchos conceptos y herramientas que desconocía y que considero esenciales en el desarrollo de software, pero que no suelen enseñarse ni en institutos ni en cursos básicos.

Gran parte del tiempo lo dediqué a investigar, resolver errores y comprender herramientas nuevas. Por ejemplo, tuve dificultades al escribir tests unitarios, porque no había planteado bien la escalabilidad desde el inicio. También aprendí que los tests que había hecho con Appium no eran robustos: al cambiar pequeños detalles de la interfaz en el emulador de los flujos de trabajo, los elementos definidos por xPath dejaban de funcionar.

Este tipo de problemas solo los ves cuando te enfrentas a ellos de verdad. Si eres junior o estás empezando, te animo a crear tu propio proyecto desde cero, pensando como si lo fueras a publicar: incluyendo testing, arquitectura limpia y flujos de trabajo. Aprenderás muchísimo, te lo aseguro :D

📝 *PD: Verás unos 300 commits relacionados con workflows. ¡Fue parte del aprendizaje!*

## Librerías principales 
- API de prueba -> https://jsonplaceholder.typicode.com/
- Navegación web para Multiplatform -> https://github.com/KevinnZou/compose-webview-multiplatform/tree/main
- JUnit test -> https://mvnrepository.com/artifact/org.jetbrains.compose.ui/ui-test-junit4
- Persistencia de datos -> https://github.com/russhwolf/multiplatform-settings
- Ktor -> https://ktor.io/docs/client-serialization.html#register_cbor
- Navigation Compose -> https://mvnrepository.com/artifact/org.jetbrains.androidx.navigation/navigation-compose
- Appium para testing -> https://appium.io/docs/en/2.2/quickstart/

## Conceptos que se han visto
- [Git](https://learngitbranching.js.org/?locale=es_ES)
- [TDD](https://www.browserstack.com/guide/what-is-test-driven-development)
- [Conventional commits](https://www.conventionalcommits.org/en/v1.0.0/)
- [Key events de Android](https://elementalx.org/button-mapper/android-key-codes/)
- [Kinds of static analyses](https://github.com/readme/guides/formatters-linters-compilers)
- [Principios de Clean Architecture](https://medium.com/@diego.coder/introducci%C3%B3n-a-las-clean-architectures-723fe9fe17fa)
- [SOLID](https://secture.com/solid-dependency-inversion-principle/)
- [CI/CD](https://www.reddit.com/r/devops/comments/t5nufe/eli5_what_is_cicd_and_why_do_we_need_them/)
- [Tokens](https://dev.to/jeanvittory/jwt-refresh-tokens-2g3d)
- [Github Actions](https://docs.github.com/en/actions/writing-workflows/quickstart)
- [Estructura MVVM](https://medium.com/@ashfaque-khokhar/android-mvvm-and-repositories-folder-structure-7de1a2dbb825)

## Arquitectura
Se implementa el patrón **MVVM (Model - View - ViewModel)**.

## Directory tree
![Esquema](directory_tree.png)

> Árbol generado con: https://tree.nathanfriend.com/
