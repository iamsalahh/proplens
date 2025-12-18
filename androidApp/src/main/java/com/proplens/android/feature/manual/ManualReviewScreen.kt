package com.proplens.android.feature.manual

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proplens.android.R

data class ManualReviewUiState(
    val propertyType: String = "Apartment",
    val title: String = "2 Bed Luxury Suite",
    val location: String = "Dubai Marina, UAE",
    val bedrooms: String = "2",
    val bathrooms: String = "3",
    val size: String = "1450",
    val listingPrice: String = "2,500,000",
    val expectedRent: String = "",
    val serviceCharge: String = "",
    val additionalCosts: String = ""
)

@Composable
fun ManualEntryReviewRoute(
    onBack: () -> Unit,
    onAnalyze: () -> Unit
) {
    var state by remember { mutableStateOf(ManualReviewUiState()) }
    ManualEntryReviewScreen(
        state = state,
        onStateChange = { state = it },
        onBack = onBack,
        onAnalyze = onAnalyze
    )
}

@Composable
fun ManualEntryReviewScreen(
    state: ManualReviewUiState,
    onStateChange: (ManualReviewUiState) -> Unit,
    onBack: () -> Unit,
    onAnalyze: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ReviewHeader(onBack = onBack, textPrimary = textPrimary)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PropertySummaryCard(
                state = state,
                primary = primary,
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border
            )
            CoreDataSection(
                state = state,
                onStateChange = onStateChange,
                primary = primary,
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border
            )
            DividerLine(border = border)
            PurchasePriceSection(
                listingPrice = state.listingPrice,
                onChange = { onStateChange(state.copy(listingPrice = it)) },
                primary = primary,
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border
            )
            DividerLine(border = border)
            IncomeExpensesSection(
                state = state,
                onStateChange = onStateChange,
                primary = primary,
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border
            )
        }
        AnalyzeFooter(onAnalyze = onAnalyze, primary = primary)
    }
}

@Composable
private fun ReviewHeader(onBack: () -> Unit, textPrimary: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = null,
            tint = textPrimary,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable(onClick = onBack)
                .padding(10.dp)
        )
        Text(
            text = stringResource(id = R.string.manual_review_title),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = textPrimary,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(40.dp))
    }
}

@Composable
private fun PropertySummaryCard(
    state: ManualReviewUiState,
    primary: Color,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(surface)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.linearGradient(
                        listOf(primary.copy(alpha = 0.35f), primary.copy(alpha = 0.15f))
                    )
                )
                .border(width = 1.dp, color = border, shape = RoundedCornerShape(12.dp))
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = state.propertyType,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = primary
            )
            Text(
                text = state.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textPrimary,
                maxLines = 1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = textMuted,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = state.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = textMuted,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun CoreDataSection(
    state: ManualReviewUiState,
    onStateChange: (ManualReviewUiState) -> Unit,
    primary: Color,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(imageVector = Icons.Filled.Apartment, contentDescription = null, tint = primary)
            Text(
                text = stringResource(id = R.string.manual_core_data),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textPrimary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ReviewTextField(
                label = stringResource(id = R.string.manual_property_type),
                value = state.propertyType,
                onChange = { onStateChange(state.copy(propertyType = it)) },
                surface = surface,
                border = border,
                textPrimary = textPrimary,
                textMuted = textMuted,
                modifier = Modifier.weight(1f)
            )
            ReviewTextField(
                label = stringResource(id = R.string.manual_location_label_simple),
                value = state.location,
                onChange = { onStateChange(state.copy(location = it)) },
                surface = surface,
                border = border,
                textPrimary = textPrimary,
                textMuted = textMuted,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ReviewTextField(
                label = stringResource(id = R.string.manual_bedrooms_label),
                value = state.bedrooms,
                onChange = { onStateChange(state.copy(bedrooms = it)) },
                surface = surface,
                border = border,
                textPrimary = textPrimary,
                textMuted = textMuted,
                modifier = Modifier.weight(1f)
            )
            ReviewTextField(
                label = stringResource(id = R.string.manual_bathrooms_label),
                value = state.bathrooms,
                onChange = { onStateChange(state.copy(bathrooms = it)) },
                surface = surface,
                border = border,
                textPrimary = textPrimary,
                textMuted = textMuted,
                modifier = Modifier.weight(1f)
            )
            ReviewTextField(
                label = stringResource(id = R.string.manual_size_label),
                value = state.size,
                onChange = { onStateChange(state.copy(size = it)) },
                surface = surface,
                border = border,
                textPrimary = textPrimary,
                textMuted = textMuted,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PurchasePriceSection(
    listingPrice: String,
    onChange: (String) -> Unit,
    primary: Color,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(imageVector = Icons.Filled.Payments, contentDescription = null, tint = primary)
                Text(
                    text = stringResource(id = R.string.manual_purchase_price),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = textPrimary
                )
            }
            Text(
                text = stringResource(id = R.string.manual_mandatory),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFFEF4444),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEF4444).copy(alpha = 0.1f))
                    .border(width = 1.dp, color = Color(0xFFEF4444).copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        OutlinedTextField(
            value = listingPrice,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = surface,
                unfocusedContainerColor = surface,
                cursorColor = primary,
                focusedTextColor = textPrimary,
                unfocusedTextColor = textPrimary
            ),
            leadingIcon = {
                Text(
                    text = "AED",
                    color = textMuted,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = textPrimary
            ),
            placeholder = { Text(text = "0", color = textMuted) }
        )
    }
}

@Composable
private fun IncomeExpensesSection(
    state: ManualReviewUiState,
    onStateChange: (ManualReviewUiState) -> Unit,
    primary: Color,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(imageVector = Icons.Filled.TrendingUp, contentDescription = null, tint = primary)
            Column {
                Text(
                    text = stringResource(id = R.string.manual_income_expenses),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = textPrimary
                )
                Text(
                    text = stringResource(id = R.string.manual_income_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = textMuted
                )
            }
        }
        ReviewTextField(
            label = stringResource(id = R.string.manual_expected_rent),
            value = state.expectedRent,
            onChange = { onStateChange(state.copy(expectedRent = it)) },
            surface = surface,
            border = border,
            textPrimary = textPrimary,
            textMuted = textMuted
        )
        ReviewTextField(
            label = stringResource(id = R.string.manual_service_charge),
            value = state.serviceCharge,
            onChange = { onStateChange(state.copy(serviceCharge = it)) },
            surface = surface,
            border = border,
            textPrimary = textPrimary,
            textMuted = textMuted
        )
        ReviewTextField(
            label = stringResource(id = R.string.manual_additional_costs),
            value = state.additionalCosts,
            onChange = { onStateChange(state.copy(additionalCosts = it)) },
            surface = surface,
            border = border,
            textPrimary = textPrimary,
            textMuted = textMuted
        )
    }
}

@Composable
private fun ReviewTextField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    surface: Color,
    border: Color,
    textPrimary: Color,
    textMuted: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = textMuted
        )
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = surface,
                unfocusedContainerColor = surface,
                cursorColor = textPrimary,
                focusedTextColor = textPrimary,
                unfocusedTextColor = textPrimary
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = textPrimary),
            placeholder = { Text(text = "", color = textMuted.copy(alpha = 0.6f)) }
        )
    }
}

@Composable
private fun DividerLine(border: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(border)
    )
}

@Composable
private fun AnalyzeFooter(
    onAnalyze: () -> Unit,
    primary: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Button(
            onClick = onAnalyze,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Analytics,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.manual_analyze_deal),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
