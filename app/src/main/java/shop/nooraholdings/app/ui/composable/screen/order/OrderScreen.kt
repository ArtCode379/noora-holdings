package shop.nooraholdings.app.ui.composable.screen.order

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import shop.nooraholdings.app.R
import shop.nooraholdings.app.data.entity.OrderEntity
import shop.nooraholdings.app.ui.composable.shared.DataBasedContainer
import shop.nooraholdings.app.ui.composable.shared.DataEmptyContent
import shop.nooraholdings.app.ui.state.DataUiState
import shop.nooraholdings.app.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    OrdersContent(
        ordersState = ordersState,
        modifier = modifier,
    )
}

@Composable
private fun OrdersContent(
    ordersState: DataUiState<List<OrderEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = ordersState,
            modifier = Modifier.fillMaxSize(),

            dataPopulated = {
                val orders = (ordersState as DataUiState.Populated).data
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    items(items = orders, key = { it.orderNumber }) { order ->
                        OrderCard(
                            order = order,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.orders_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun OrderCard(
    order: OrderEntity,
    modifier: Modifier = Modifier,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.order_number, order.orderNumber),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFF5EDD5),
                            shape = RoundedCornerShape(12.dp),
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = "Awaiting Collection",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFC9A961),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outline),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${order.customerFirstName} ${order.customerLastName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = order.timestamp.format(dateFormatter),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = order.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "£%.2f".format(order.price),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
