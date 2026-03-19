package shop.nooraholdings.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val NooraColorScheme = lightColorScheme(
    primary = NooraGold,
    onPrimary = NooraDark,
    primaryContainer = NooraGoldPale,
    onPrimaryContainer = NooraDark,
    secondary = NooraDark,
    onSecondary = NooraBackground,
    secondaryContainer = NooraDarkSurface,
    onSecondaryContainer = NooraBackground,
    tertiary = NooraGoldLight,
    onTertiary = NooraDark,
    background = NooraBackground,
    onBackground = NooraText,
    surface = NooraSurface,
    onSurface = NooraText,
    surfaceVariant = NooraSurfaceVariant,
    onSurfaceVariant = NooraTextLight,
    outline = NooraOutline,
    error = NooraError,
    onError = NooraSurface,
)

private val NooraShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

@Composable
fun ProductAppNORHDTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = NooraColorScheme,
        typography = Typography,
        shapes = NooraShapes,
        content = content,
    )
}
