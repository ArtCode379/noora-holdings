package shop.nooraholdings.app.ui.composable.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import shop.nooraholdings.app.R
import shop.nooraholdings.app.ui.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboardedState by viewModel.onboardedState.collectAsStateWithLifecycle()
    var logoVisible by remember { mutableStateOf(false) }
    val logoAlpha by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "logo_fade",
    )

    LaunchedEffect(Unit) {
        logoVisible = true
        delay(2000)
        if (onboardedState) {
            onNavigateToHomeScreen()
        } else {
            onNavigateToOnboarding()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A3C5E),
                        Color(0xFF2C5F8A),
                    )
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.icon),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .alpha(logoAlpha),
            contentScale = ContentScale.Fit,
        )
    }
}
