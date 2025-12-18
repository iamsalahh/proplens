package com.proplens.android.feature.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.RealEstateAgent
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

enum class TransactionType { Sale, Rental }

data class TransactionItem(
    val type: TransactionType,
    val date: String,
    val price: String,
    val pricePerSqft: String,
    val beds: String,
    val baths: String,
    val size: String,
    val status: String? = null
)

@Composable
fun TransactionsRoute(
    onBack: () -> Unit
) {
    val filters = listOf(
        stringResource(id = R.string.transactions_filter_all),
        stringResource(id = R.string.transactions_filter_sales),
        stringResource(id = R.string.transactions_filter_rentals),
        stringResource(id = R.string.transactions_filter_last_12)
    )
    var selectedFilter by remember { mutableStateOf(filters.first()) }

    val items = listOf(
        TransactionItem(
            type = TransactionType.Sale,
            date = stringResource(id = R.string.transactions_date_1),
            price = stringResource(id = R.string.transactions_price_1),
            pricePerSqft = stringResource(id = R.string.transactions_price_sqft_1),
            beds = stringResource(id = R.string.transactions_beds_1),
            baths = stringResource(id = R.string.transactions_baths_1),
            size = stringResource(id = R.string.transactions_size_1),
            status = stringResource(id = R.string.transactions_status_completed)
        ),
        TransactionItem(
            type = TransactionType.Rental,
            date = stringResource(id = R.string.transactions_date_2),
            price = stringResource(id = R.string.transactions_price_2),
            pricePerSqft = stringResource(id = R.string.transactions_price_sqft_2),
            beds = stringResource(id = R.string.transactions_beds_2),
            baths = stringResource(id = R.string.transactions_baths_2),
            size = stringResource(id = R.string.transactions_size_2)
        ),
        TransactionItem(
            type = TransactionType.Sale,
            date = stringResource(id = R.string.transactions_date_3),
            price = stringResource(id = R.string.transactions_price_3),
            pricePerSqft = stringResource(id = R.string.transactions_price_sqft_3),
            beds = stringResource(id = R.string.transactions_beds_3),
            baths = stringResource(id = R.string.transactions_baths_3),
            size = stringResource(id = R.string.transactions_size_3)
        ),
        TransactionItem(
            type = TransactionType.Sale,
            date = stringResource(id = R.string.transactions_date_4),
            price = stringResource(id = R.string.transactions_price_4),
            pricePerSqft = stringResource(id = R.string.transactions_price_sqft_4),
            beds = stringResource(id = R.string.transactions_beds_4),
            baths = stringResource(id = R.string.transactions_baths_4),
            size = stringResource(id = R.string.transactions_size_4)
        )
    )

    TransactionsScreen(
        filters = filters,
        selectedFilter = selectedFilter,
        onFilterSelected = { selectedFilter = it },
        transactions = items,
        onBack = onBack
    )
}

@Composable
fun TransactionsScreen(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    transactions: List<TransactionItem>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)
    val success = colorResource(id = R.color.proplens_positive_green)

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
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
                    text = stringResource(id = R.string.transactions_title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = textPrimary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(40.dp))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text(
                        text = stringResource(id = R.string.transactions_property_title),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = textPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.transactions_property_subtitle),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = textMuted
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    filters.forEach { filter ->
                        val selected = filter == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (selected) primary else surface)
                                .border(
                                    width = if (selected) 0.dp else 1.dp,
                                    color = border,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { onFilterSelected(filter) }
                                .padding(horizontal = 18.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = filter,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                                ),
                                color = if (selected) Color.White else textPrimary
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatTile(
                        icon = Icons.Filled.Analytics,
                        title = stringResource(id = R.string.transactions_avg_price),
                        value = stringResource(id = R.string.transactions_avg_price_value),
                        note = stringResource(id = R.string.transactions_avg_price_note),
                        primary = primary,
                        success = success,
                        surface = surface,
                        border = border,
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        icon = Icons.Filled.AccountBalanceWallet,
                        title = stringResource(id = R.string.transactions_total_volume),
                        value = stringResource(id = R.string.transactions_total_volume_value),
                        note = stringResource(id = R.string.transactions_total_volume_note),
                        primary = primary,
                        success = success,
                        surface = surface,
                        border = border,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.transactions_recent_header),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = textPrimary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.transactions_sort),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = primary
                        )
                        Icon(
                            imageVector = Icons.Filled.Sort,
                            contentDescription = null,
                            tint = primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            items(transactions.size) { index ->
                val item = transactions[index]
                TransactionCard(
                    item = item,
                    primary = primary,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    success = success,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun StatTile(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    note: String,
    primary: Color,
    success: Color,
    surface: Color,
    border: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(surface)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = primary)
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = colorResource(id = R.color.proplens_text_muted)
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.proplens_text_primary)
        )
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(imageVector = Icons.Filled.TrendingUp, contentDescription = null, tint = success, modifier = Modifier.size(16.dp))
            Text(
                text = note,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                color = success
            )
        }
    }
}

@Composable
private fun TransactionCard(
    item: TransactionItem,
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    success: Color,
    modifier: Modifier = Modifier
) {
    val typeColor = if (item.type == TransactionType.Sale) primary else Color(0xFFF97316)
    val icon = if (item.type == TransactionType.Sale) Icons.Filled.RealEstateAgent else Icons.Filled.Key

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.proplens_card_surface))
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(width = 1.dp, color = border, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = typeColor)
                }
                Column {
                    Text(
                        text = if (item.type == TransactionType.Sale) {
                            stringResource(id = R.string.transactions_type_sale)
                        } else {
                            stringResource(id = R.string.transactions_type_rental)
                        },
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = typeColor
                    )
                    Text(
                        text = item.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = textMuted
                    )
                }
            }
            if (item.status != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(success.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.status,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = success
                    )
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = item.price,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = textPrimary
            )
            Text(
                text = item.pricePerSqft,
                style = MaterialTheme.typography.bodySmall,
                color = textMuted
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .border(width = 1.dp, color = border, shape = RoundedCornerShape(12.dp))
                .background(Color.Transparent)
                .padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TransactionMeta(icon = Icons.Filled.Hotel, text = item.beds, textPrimary = textPrimary, textMuted = textMuted)
            TransactionMeta(icon = Icons.Filled.Bathtub, text = item.baths, textPrimary = textPrimary, textMuted = textMuted)
            TransactionMeta(icon = Icons.Filled.Straighten, text = item.size, textPrimary = textPrimary, textMuted = textMuted)
        }
    }
}

@Composable
private fun TransactionMeta(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    textPrimary: Color,
    textMuted: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = textMuted, modifier = Modifier.size(18.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = textPrimary
        )
    }
}
