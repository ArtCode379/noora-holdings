package shop.nooraholdings.app.ui.composable.screen.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import shop.nooraholdings.app.R
import shop.nooraholdings.app.data.model.Product
import shop.nooraholdings.app.ui.composable.shared.DataBasedContainer
import shop.nooraholdings.app.ui.composable.shared.DataEmptyContent
import shop.nooraholdings.app.ui.state.DataUiState
import shop.nooraholdings.app.ui.viewmodel.ProductDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel(),
) {
    val productState by viewModel.productDetailsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeProductDetails(productId)
    }

    ProductDetailsScreenContent(
        productState = productState,
        modifier = modifier,
        onAddToCart = viewModel::addProductToCart
    )
}

@Composable
private fun ProductDetailsScreenContent(
    productState: DataUiState<Product>,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
) {
    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = productState,

            dataPopulated = {
                val product = (productState as DataUiState.Populated).data
                ProductDetailsBody(
                    product = product,
                    onAddToCart = onAddToCart,
                )
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.product_details_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ProductDetailsBody(
    product: Product,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = stringResource(R.string.product_image_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    text = stringResource(product.category.titleRes),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "£%.2f".format(product.price),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outline),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                ),
            ) {
                Text(
                    text = stringResource(R.string.button_add_to_cart_label),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
