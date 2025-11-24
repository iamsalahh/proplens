package com.proplens.android.ui.adddeal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proplens.android.viewmodel.AddDealEvent
import com.proplens.android.viewmodel.AddDealState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AddDealScreen(
    state: StateFlow<AddDealState>,
    onEvent: (AddDealEvent) -> Unit,
    onBack: () -> Unit
) {
    val uiState by state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Deal") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = uiState.title,
                onValueChange = { onEvent(AddDealEvent.TitleChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Title") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = uiState.area,
                onValueChange = { onEvent(AddDealEvent.AreaChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Area") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = uiState.bedrooms,
                onValueChange = { onEvent(AddDealEvent.BedroomsChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Bedrooms") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = uiState.sizeSqft,
                onValueChange = { onEvent(AddDealEvent.SizeChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Size (sqft)") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = uiState.askingPrice,
                onValueChange = { onEvent(AddDealEvent.AskingPriceChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Asking Price (AED)") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = uiState.expectedRent,
                onValueChange = { onEvent(AddDealEvent.ExpectedRentChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Expected Rent / Year (AED)") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = uiState.serviceCharge,
                onValueChange = { onEvent(AddDealEvent.ServiceChargeChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Service Charge / Year (AED)") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = uiState.additionalCosts,
                onValueChange = { onEvent(AddDealEvent.AdditionalCostsChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Additional Costs / Year (AED)") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            uiState.analysis?.let { analysis ->
                Text(text = "Price per sqft: ${String.format("%.2f", analysis.pricePerSqft)}")
                Text(text = "Gross yield: ${String.format("%.2f", analysis.grossYieldPercent)}%")
                Text(text = "Net yield: ${String.format("%.2f", analysis.netYieldPercent)}%")
                Text(text = "Monthly cashflow: ${String.format("%.2f", analysis.monthlyCashflowEstimate)}")
                Text(
                    text = "Verdict: ${analysis.verdict}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = { onEvent(AddDealEvent.Save) }, modifier = Modifier.fillMaxWidth()) {
                Text(text = if (uiState.isSaving) "Saving..." else "Save Deal")
            }
        }
    }
}
