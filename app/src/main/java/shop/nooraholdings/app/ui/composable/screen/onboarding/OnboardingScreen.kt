package shop.nooraholdings.app.ui.composable.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import shop.nooraholdings.app.R
import shop.nooraholdings.app.ui.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

data class OnboardingContent(
    @field:StringRes val titleRes: Int,
    @field:StringRes val descriptionRes: Int,
    @field:DrawableRes val imageRes: Int
)

private val onboardingPagesContent = listOf(
    OnboardingContent(
        titleRes = R.string.page_1_title,
        descriptionRes = R.string.page_1_description,
        imageRes = R.drawable.onboarding_living_room,
    ),
    OnboardingContent(
        titleRes = R.string.page_2_title,
        descriptionRes = R.string.page_2_description,
        imageRes = R.drawable.onboarding_kitchen,
    ),
    OnboardingContent(
        titleRes = R.string.page_3_title,
        descriptionRes = R.string.page_3_description,
        imageRes = R.drawable.onboarding_home_decor,
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()

    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) {
            onNavigateToHomeScreen()
        }
    }

    val pagerState = rememberPagerState(pageCount = { onboardingPagesContent.size })
    val coroutineScope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == onboardingPagesContent.lastIndex

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            OnboardingPage(content = onboardingPagesContent[page])
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000)),
                    )
                )
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(onboardingPagesContent.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 10.dp else 6.dp)
                            .background(
                                color = if (isSelected) Color(0xFFC9A961) else Color(0x80FFFFFF),
                                shape = CircleShape,
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isLastPage) {
                        viewModel.setOnboarded()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC9A961),
                    contentColor = Color(0xFF1A1A1A),
                ),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(
                    text = if (isLastPage) {
                        stringResource(R.string.start_button_title)
                    } else {
                        stringResource(R.string.next_button_title)
                    },
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun OnboardingPage(
    content: OnboardingContent,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(content.imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xCC000000), Color.Transparent),
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 72.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(content.titleRes),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(content.descriptionRes),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xCCFFFFFF),
                textAlign = TextAlign.Center,
            )
        }
    }
}
