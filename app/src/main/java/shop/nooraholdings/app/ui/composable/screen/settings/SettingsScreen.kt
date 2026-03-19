package shop.nooraholdings.app.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import shop.nooraholdings.app.R

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
    ) {
        Text(
            text = stringResource(R.string.settings_about_label),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp),
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Column {
                SettingsRow(
                    label = stringResource(R.string.settings_screen_company_label),
                    value = stringResource(R.string.company_name),
                )
                SettingsDivider()
                SettingsRow(
                    label = stringResource(R.string.settings_screen_version_label),
                    value = stringResource(R.string.app_version),
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.settings_screen_customer_support_label),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp),
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            val supportUrl = stringResource(R.string.customer_support_link)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(supportUrl))
                        context.startActivity(intent)
                    }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.link_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.settings_visit_support_label),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = supportUrl,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.company_name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "v${stringResource(R.string.app_version)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun SettingsRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SettingsDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(start = 16.dp)
            .background(MaterialTheme.colorScheme.outline),
    )
}
