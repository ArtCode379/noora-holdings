package shop.nooraholdings.app.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import shop.nooraholdings.app.R
import shop.nooraholdings.app.data.entity.OrderEntity
import shop.nooraholdings.app.ui.state.DataUiState
import shop.nooraholdings.app.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalidState by viewModel.emailInvalidState.collectAsStateWithLifecycle()

    val isButtonEnabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotEmpty() &&
                    viewModel.customerLastName.isNotEmpty() &&
                    viewModel.customerEmail.isNotEmpty()
        }
    }

    if (orderState is DataUiState.Populated) {
        CheckoutDialog(
            order = (orderState as DataUiState.Populated<OrderEntity>).data,
            onConfirm = onNavigateToOrdersScreen
        )
    }

    CheckoutContent(
        customerFirstName = viewModel.customerFirstName,
        customerLastName = viewModel.customerLastName,
        customerEmail = viewModel.customerEmail,
        isEmailInvalid = emailInvalidState,
        modifier = modifier,
        focusManager = focusManager,
        isButtonEnabled = isButtonEnabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPlaceOrderButtonClick = viewModel::placeOrder
    )
}

@Composable
private fun CheckoutContent(
    customerFirstName: String,
    customerLastName: String,
    customerEmail: String,
    isEmailInvalid: Boolean,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPlaceOrderButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
    ) {
        Text(
            text = stringResource(R.string.checkout_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(24.dp))

        CheckoutTextField(
            input = customerFirstName,
            onInputChange = onFirstNameChanged,
            labelText = stringResource(R.string.checkout_text_field_first_name),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
            ),
        )

        Spacer(modifier = Modifier.height(12.dp))

        CheckoutTextField(
            input = customerLastName,
            onInputChange = onLastNameChanged,
            labelText = stringResource(R.string.checkout_text_field_last_name),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
            ),
        )

        Spacer(modifier = Modifier.height(12.dp))

        CheckoutTextField(
            input = customerEmail,
            onInputChange = onEmailChanged,
            labelText = stringResource(R.string.checkout_text_field_email),
            modifier = Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
        )

        if (isEmailInvalid) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Please enter a valid email address",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                onPlaceOrderButtonClick()
            },
            enabled = isButtonEnabled,
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
                text = stringResource(R.string.button_confirm_order_label),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleSmall,
            )
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
    )
}
