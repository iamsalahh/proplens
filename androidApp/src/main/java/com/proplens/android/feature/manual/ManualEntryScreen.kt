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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proplens.android.R


@Composable
fun ManualEntryRoute(
    onClose: () -> Unit,
    onCancel: () -> Unit,
    onContinue: () -> Unit
) {
    ManualEntryScreen(
        onClose = onClose,
        onCancel = onCancel,
        onContinue = onContinue
    )
}

@Composable
fun ManualEntryScreen(
    onClose: () -> Unit,
    onCancel: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)

    var location by remember { mutableStateOf("") }
    var bedrooms by remember { mutableStateOf("1") }
    var size by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header(
            onClose = onClose,
            onCancel = onCancel,
            textPrimary = textPrimary,
            textMuted = textMuted,
            primary = primary,
            surface = surface,
            border = border
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle(textPrimary = textPrimary, textMuted = textMuted, primary = primary)
            LocationField(
                value = location,
                onValueChange = { location = it },
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border
            )
            BedroomChips(
                selected = bedrooms,
                onSelect = { bedrooms = it },
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border,
                primary = primary
            )
            SizePriceFields(
                size = size,
                onSizeChange = { size = it },
                price = price,
                onPriceChange = { price = it },
                surface = surface,
                textPrimary = textPrimary,
                textMuted = textMuted,
                border = border,
                primary = primary
            )
        }

        ContinueBar(onContinue = onContinue, primary = primary)
    }
}

@Composable
private fun Header(
    onClose: () -> Unit,
    onCancel: () -> Unit,
    textPrimary: Color,
    textMuted: Color,
    primary: Color,
    surface: Color,
    border: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = textMuted,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClose)
                    .padding(10.dp)
            )
            Text(
                text = stringResource(id = R.string.manual_add_property),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textPrimary
            )
            Text(
                text = stringResource(id = R.string.manual_cancel),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = textMuted,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onCancel)
                    .padding(10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(primary)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(surface)
                    .border(width = 1.dp, color = border, shape = RoundedCornerShape(10.dp))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(surface)
                    .border(width = 1.dp, color = border, shape = RoundedCornerShape(10.dp))
            )
        }
    }
}

@Composable
private fun SectionTitle(
    textPrimary: Color,
    textMuted: Color,
    primary: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "1",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = primary
                )
            }
            Text(
                text = stringResource(id = R.string.manual_section_label),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = primary
            )
        }
        Text(
            text = stringResource(id = R.string.manual_section_title),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = textPrimary
        )
        Text(
            text = stringResource(id = R.string.manual_section_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = textMuted
        )
    }
}

@Composable
private fun LabelWithRequired(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(text = "*", color = Color(0xFFEF4444))
    }
}

@Composable
private fun LocationField(
    value: String,
    onValueChange: (String) -> Unit,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LabelWithRequired(text = stringResource(id = R.string.manual_location_label))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = textMuted
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.manual_location_placeholder),
                    color = textMuted
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = textMuted
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = surface,
                unfocusedContainerColor = surface,
                cursorColor = textPrimary
            )
        )
    }
}

@Composable
private fun BedroomChips(
    selected: String,
    onSelect: (String) -> Unit,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    primary: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LabelWithRequired(text = stringResource(id = R.string.manual_bedrooms_label))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(surface)
                .border(width = 1.dp, color = border, shape = RoundedCornerShape(18.dp))
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("Studio", "1", "2", "3", "4+").forEach { option ->
                val isSelected = option == selected
                Text(
                    text = option,
                    color = if (isSelected) primary else textMuted,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) Color.White else Color.Transparent)
                        .border(
                            width = if (isSelected) 1.dp else 0.dp,
                            color = if (isSelected) border else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelect(option) }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
private fun SizePriceFields(
    size: String,
    onSizeChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    surface: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    primary: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = size,
            onValueChange = onSizeChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.SquareFoot,
                    contentDescription = null,
                    tint = textMuted
                )
            },
            label = {
                LabelWithRequired(text = stringResource(id = R.string.manual_size_label))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.manual_size_placeholder),
                    color = textMuted.copy(alpha = 0.6f)
                )
            },
            trailingIcon = {
                Text(
                    text = stringResource(id = R.string.manual_size_unit),
                    color = textMuted,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = surface,
                unfocusedContainerColor = surface,
                cursorColor = textPrimary
            )
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = price,
                onValueChange = onPriceChange,
                leadingIcon = {
                    Text(
                        text = "AED",
                        color = textMuted,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                label = {
                    LabelWithRequired(text = stringResource(id = R.string.manual_price_label))
                },
                placeholder = {
                    Text(
                        text = "0",
                        color = textMuted.copy(alpha = 0.6f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = surface,
                    unfocusedContainerColor = surface,
                    cursorColor = textPrimary
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(primary.copy(alpha = 0.06f))
                    .border(width = 1.dp, color = primary.copy(alpha = 0.12f), shape = RoundedCornerShape(12.dp))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.SquareFoot,
                    contentDescription = null,
                    tint = primary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = stringResource(id = R.string.manual_price_hint),
                    style = MaterialTheme.typography.labelMedium,
                    color = textPrimary
                )
            }
        }
    }
}

@Composable
private fun ContinueBar(
    onContinue: () -> Unit,
    primary: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.manual_continue),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ManualEntryDetailsPlaceholder(
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.placeholder_title),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = stringResource(id = R.string.placeholder_body),
                color = colorResource(id = R.color.proplens_text_muted),
                textAlign = TextAlign.Center
            )
            Button(onClick = onBack) {
                Text(text = stringResource(id = R.string.placeholder_back))
            }
        }
    }
}

@Composable
fun ManualEntryDetailsRoute(
    onBack: () -> Unit,
    onSkip: () -> Unit,
    onContinue: () -> Unit
) {
    ManualEntryDetailsScreen(
        onBack = onBack,
        onSkip = onSkip,
        onContinue = onContinue
    )
}

@Composable
fun ManualEntryDetailsScreen(
    onBack: () -> Unit,
    onSkip: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)

    var rent by remember { mutableStateOf("120000") }
    var serviceCharge by remember { mutableStateOf("15000") }
    var additionalCosts by remember { mutableStateOf("0") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DetailsHeader(
            onBack = onBack,
            onSkip = onSkip,
            textPrimary = textPrimary,
            textMuted = textMuted,
            primary = primary,
            border = border,
            surface = surface
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = stringResource(id = R.string.manual_refine_title),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = textPrimary
            )
            Text(
                text = stringResource(id = R.string.manual_refine_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = textMuted
            )
            FieldWithBadge(
                label = stringResource(id = R.string.manual_expected_rent),
                value = rent,
                onValueChange = { rent = it },
                unit = stringResource(id = R.string.manual_per_year),
                primary = primary,
                textPrimary = textPrimary,
                textMuted = textMuted,
                surface = surface,
                border = border
            )
            FieldWithBadge(
                label = stringResource(id = R.string.manual_service_charge),
                value = serviceCharge,
                onValueChange = { serviceCharge = it },
                unit = stringResource(id = R.string.manual_per_year),
                primary = primary,
                textPrimary = textPrimary,
                textMuted = textMuted,
                surface = surface,
                border = border
            )
            FieldWithBadge(
                label = stringResource(id = R.string.manual_additional_costs),
                value = additionalCosts,
                onValueChange = { additionalCosts = it },
                unit = stringResource(id = R.string.manual_per_year),
                primary = primary,
                textPrimary = textPrimary,
                textMuted = textMuted,
                surface = surface,
                border = border,
                helper = stringResource(id = R.string.manual_additional_hint)
            )
        }
        DetailsBottomBar(
            onContinue = onContinue,
            onSkip = onSkip,
            primary = primary
        )
    }
}

@Composable
private fun DetailsHeader(
    onBack: () -> Unit,
    onSkip: () -> Unit,
    textPrimary: Color,
    textMuted: Color,
    primary: Color,
    border: Color,
    surface: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = textPrimary,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .padding(10.dp)
            )
            Text(
                text = stringResource(id = R.string.manual_add_property),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textPrimary
            )
            Text(
                text = stringResource(id = R.string.manual_skip),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = primary,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onSkip)
                    .padding(10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(primary.copy(alpha = 0.4f))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(primary)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(surface)
                    .border(width = 1.dp, color = border, shape = RoundedCornerShape(10.dp))
            )
        }
    }
}

@Composable
private fun FieldWithBadge(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    surface: Color,
    border: Color,
    helper: String? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = textPrimary
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDCFCE7))
                    .border(width = 1.dp, color = Color(0xFFA7F3D0), shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = stringResource(id = R.string.manual_improves_accuracy),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF16A34A)
                )
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = surface,
                unfocusedContainerColor = surface,
                cursorColor = primary
            ),
            placeholder = {
                Text(text = "0", color = textMuted)
            },
            trailingIcon = {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodySmall,
                    color = textMuted
                )
            }
        )
        if (helper != null) {
            Text(
                text = helper,
                style = MaterialTheme.typography.labelSmall,
                color = textMuted
            )
        }
    }
}

@Composable
private fun DetailsBottomBar(
    onContinue: () -> Unit,
    onSkip: () -> Unit,
    primary: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.manual_continue),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
        Text(
            text = stringResource(id = R.string.manual_skip),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onSkip)
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
fun ManualEntryReviewPlaceholder(onBack: () -> Unit) {
    ManualEntryDetailsPlaceholder(onBack = onBack)
}
