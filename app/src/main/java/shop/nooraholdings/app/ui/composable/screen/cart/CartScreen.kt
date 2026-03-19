package shop.nooraholdings.app.ui.composable.screen.cart

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import shop.nooraholdings.app.R
import shop.nooraholdings.app.ui.composable.shared.DataBasedContainer
import shop.nooraholdings.app.ui.composable.shared.DataEmptyContent
import shop.nooraholdings.app.ui.state.CartItemUiState
import shop.nooraholdings.app.ui.state.DataUiState
import shop.nooraholdings.app.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val cartItemsState by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    CartScreenContent(
        cartItemsState = cartItemsState,
        modifier = modifier,
        totalPrice = totalPrice,
        onPlusItemClick = viewModel::incrementProductInCart,
        onMinusItemClick = viewModel::decrementItemInCart,
        onCompleteOrderButtonClick = onNavigateToCheckoutScreen,
    )
}

@Composable
private fun CartScreenContent(
    cartItemsState: DataUiState<List<CartItemUiState>>,
    modifier: Modifier = Modifier,
    totalPrice: Double,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
    onCompleteOrderButtonClick: () -> Unit,
) {
    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = cartItemsState,
            modifier = Modifier.fillMaxSize(),

            dataPopulated = {
                val cartItems = (cartItemsState as DataUiState.Populated).data
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                        items(items = cartItems, key = { it.productId }) { item ->
                            CartItemRow(
                                item = item,
                                onIncrease = { onPlusItemClick(item.productId) },
                                onDecrease = { onMinusItemClick(item.productId) },
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                        }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Order Total",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                text = "£%.2f".format(totalPrice),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = onCompleteOrderButtonClick,
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
                                text = stringResource(R.string.proceed_to_checkout_label),
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.cart_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun CartItemRow(
    item: CartItemUiState,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (item.productImageRes != null) {
                Image(
                    painter = painterResource(item.productImageRes),
                    contentDescription = item.productTitle,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productTitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "£%.2f".format(item.productPrice),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onDecrease,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus_svgrepo_com),
                        contentDescription = stringResource(R.string.decrease_quantity_icon_description),
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Text(
                    text = item.quantity.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 4.dp),
                )
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.plus_svgrepo_com),
                        contentDescription = stringResource(R.string.increase_quantity_icon_description),
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}
