import org.jetbrains.compose.storytale.story

val `PrimaryButton default state` by story {
    val enabled by parameter(true)
    PrimaryButton(onClick = {}, enabled = enabled)
}