package shop.nooraholdings.app.ui.composable.screen.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import shop.nooraholdings.app.R
import shop.nooraholdings.app.data.entity.OrderEntity

@Composable
fun CheckoutDialog(
    order: OrderEntity,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(color = Color(0xFFF5EDD5), shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.calendar_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color(0xFFC9A961),
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.checkout_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = stringResource(R.string.checkout_dialog_order_number, order.orderNumber),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = stringResource(R.string.checkout_dialog_processing_message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                ) {
                    Text(
                        text = "View My Orders",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}
