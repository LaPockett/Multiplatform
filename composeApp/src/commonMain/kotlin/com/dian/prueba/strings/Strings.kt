package com.dian.prueba.strings

data class Strings(
    val translations: Map<String, String>
)

object TranslationManager {
    const val MSG1 = "¡Hola de nuevo! Aquí tienes unas recomendaciones siguiendo tu estilo habitual"
    const val MSG2 = "En qué mood estás hoy?"
    const val MSG3 = "Quiero algo que se parezca a esta foto"

    private val translations = mapOf(
        Locales.EN to mapOf(
            MSG1 to "Hello again! Here are some recommendations based on your usual style.",
            MSG2 to "What mood are you in today?",
            MSG3 to "I want something that looks like this photo",
        ),
        Locales.PT to mapOf(
            MSG1 to "Olá novamente! Aqui estão algumas recomendações de acordo com o seu estilo habitual",
            MSG2 to "Qual é o seu humor hoje?",
            MSG3 to "Quero algo parecido com esta foto",
        ),
        Locales.ES to mapOf(
            MSG1 to MSG1,
            MSG2 to MSG2,
            MSG3 to MSG3,
        ),
        Locales.FR to mapOf(
            MSG1 to "Bonjour à nouveau! Voici quelques recommandations en fonction de votre style habituel",
            MSG2 to "En quoi tu es aujourd'hui?",
            MSG3 to "Je veux quelque chose qui ressemble à cette photo",
        ),
        Locales.DE to mapOf(
            MSG1 to "Hallo zurück! Hier sind ein paar Hinweise auf deinen normalen Stil",
            MSG2 to "Wie fühlst du heute?",
            MSG3 to "Ich möchte etwas wie das Bild sein",
        ),
        Locales.ZH to mapOf(
            MSG1 to "你好呀！根据你的日常风格，这里有一些推荐",
            MSG2 to "今天心情如何？",
            MSG3 to "想要类似这张照片的东西"
        ),
        Locales.CA to mapOf(
            MSG1 to "Hola de nou! Aquí tens algunes recomanacions basades en el teu estil habitual",
            MSG2 to "Com et trobes avui?",
            MSG3 to "Vull alguna cosa semblant a aquesta foto."
        )
    )

    fun translate(text: String, language: String): String {
        return translations[language]?.get(text) ?: text
    }
}