import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PrimaryButton(onClick: () -> Unit, enabled: Boolean = true) {
    Button(onClick = onClick, enabled = enabled) {
        Text("Click me!")
    }
}