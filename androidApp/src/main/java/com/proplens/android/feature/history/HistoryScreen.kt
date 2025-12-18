package com.proplens.android.feature.history

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.proplens.android.R

enum class HistoryFilter(@StringRes val labelRes: Int) {
    All(R.string.history_filter_all),
    GoodDeal(R.string.history_filter_good_deal),
    Overpriced(R.string.history_filter_overpriced),
    HighRisk(R.string.history_filter_high_risk)
}

enum class HistoryStatus(
    @StringRes val labelRes: Int,
    @ColorRes val colorRes: Int
) {
    Excellent(R.string.history_badge_excellent, R.color.proplens_positive_green),
    Overpriced(R.string.history_badge_overpriced, R.color.proplens_warning_amber),
    HighRisk(R.string.history_badge_high_risk, R.color.proplens_error_red)
}

data class HistoryItem(
    val id: String,
    val title: String,
    val location: String,
    val roi: String,
    val price: String,
    val date: String,
    val status: HistoryStatus
)

data class HistoryUiState(
    val query: String,
    val selectedFilter: HistoryFilter,
    val items: List<HistoryItem>
)

sealed interface HistoryAction {
    data object Sort : HistoryAction
    data object ViewDetails : HistoryAction
    data object AddNew : HistoryAction
    data object NavHome : HistoryAction
    data object NavHistory : HistoryAction
    data object NavAnalyze : HistoryAction
    data object NavProfile : HistoryAction
    data object Back : HistoryAction
    data class SearchChanged(val query: String) : HistoryAction
    data class FilterSelected(val filter: HistoryFilter) : HistoryAction
}

@Composable
fun HistoryRoute(
    onAction: (HistoryAction) -> Unit = {}
) {
    var query by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf(HistoryFilter.All) }

    val items = listOf(
        HistoryItem(
            id = "1",
            title = stringResource(id = R.string.history_property_1_title),
            location = stringResource(id = R.string.history_property_1_location),
            roi = stringResource(id = R.string.history_property_1_roi),
            price = stringResource(id = R.string.history_property_1_price),
            date = stringResource(id = R.string.history_property_1_date),
            status = HistoryStatus.Excellent
        ),
        HistoryItem(
            id = "2",
            title = stringResource(id = R.string.history_property_2_title),
            location = stringResource(id = R.string.history_property_2_location),
            roi = stringResource(id = R.string.history_property_2_roi),
            price = stringResource(id = R.string.history_property_2_price),
            date = stringResource(id = R.string.history_property_2_date),
            status = HistoryStatus.Overpriced
        ),
        HistoryItem(
            id = "3",
            title = stringResource(id = R.string.history_property_3_title),
            location = stringResource(id = R.string.history_property_3_location),
            roi = stringResource(id = R.string.history_property_3_roi),
            price = stringResource(id = R.string.history_property_3_price),
            date = stringResource(id = R.string.history_property_3_date),
            status = HistoryStatus.HighRisk
        ),
        HistoryItem(
            id = "4",
            title = stringResource(id = R.string.history_property_4_title),
            location = stringResource(id = R.string.history_property_4_location),
            roi = stringResource(id = R.string.history_property_4_roi),
            price = stringResource(id = R.string.history_property_4_price),
            date = stringResource(id = R.string.history_property_4_date),
            status = HistoryStatus.Excellent
        )
    )

    val filteredItems = items.filter { item ->
        val matchesQuery = query.isBlank() ||
            item.title.contains(query, ignoreCase = true) ||
            item.location.contains(query, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            HistoryFilter.All -> true
            HistoryFilter.GoodDeal -> item.status == HistoryStatus.Excellent
            HistoryFilter.Overpriced -> item.status == HistoryStatus.Overpriced
            HistoryFilter.HighRisk -> item.status == HistoryStatus.HighRisk
        }
        matchesQuery && matchesFilter
    }

    val state = HistoryUiState(
        query = query,
        selectedFilter = selectedFilter,
        items = filteredItems
    )

    HistoryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HistoryAction.SearchChanged -> query = action.query
                is HistoryAction.FilterSelected -> selectedFilter = action.filter
                else -> onAction(action)
            }
        }
    )
}

@Composable
fun HistoryScreen(
    state: HistoryUiState,
    onAction: (HistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val background = colorResource(id = R.color.proplens_background_gray)
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_color)

    Scaffold(
        modifier = modifier,
        containerColor = background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(HistoryAction.AddNew) },
                containerColor = primary,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            HistoryBottomNav(
                primary = primary,
                muted = textMuted,
                border = border,
                onAction = onAction
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background)
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.history_title),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = textPrimary
                    )
                    IconButton(onClick = { onAction(HistoryAction.Sort) }) {
                        Icon(
                            imageVector = Icons.Filled.Sort,
                            contentDescription = null,
                            tint = textPrimary
                        )
                    }
                }
            }
            item {
                TextField(
                    value = state.query,
                    onValueChange = { onAction(HistoryAction.SearchChanged(it)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = textMuted
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.history_search_hint),
                            color = textMuted
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedContainerColor = surface,
                        unfocusedContainerColor = surface,
                        disabledContainerColor = surface,
                        cursorColor = primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(52.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(width = 1.dp, color = border, shape = RoundedCornerShape(18.dp))
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HistoryFilter.values().forEach { filter ->
                        HistoryFilterChip(
                            text = stringResource(id = filter.labelRes),
                            selected = filter == state.selectedFilter,
                            onClick = { onAction(HistoryAction.FilterSelected(filter)) },
                            textPrimary = textPrimary,
                            textMuted = textMuted,
                            surface = surface,
                            border = border
                        )
                    }
                }
            }
            items(state.items, key = { it.id }) { item ->
                HistoryCard(
                    item = item,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    onViewDetails = { onAction(HistoryAction.ViewDetails) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HistoryFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    textPrimary: Color,
    textMuted: Color,
    surface: Color,
    border: Color
) {
    val background = if (selected) textPrimary else surface
    val contentColor = if (selected) Color.White else textMuted
    val borderColor = if (selected) Color.Transparent else border

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(background)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = contentColor
        )
    }
}

@Composable
private fun HistoryCard(
    item: HistoryItem,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    onViewDetails: () -> Unit
) {
    val statusColor = colorResource(id = item.status.colorRes)

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(24.dp))
            .clickable(onClick = onViewDetails)
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(statusColor.copy(alpha = 0.35f), statusColor.copy(alpha = 0.1f))
                        )
                    )
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.location,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                            color = textMuted
                        )
                    }
                    StatusBadge(
                        text = stringResource(id = item.status.labelRes),
                        color = statusColor
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatBlock(
                        label = stringResource(id = R.string.history_label_roi),
                        value = item.roi,
                        valueColor = statusColor,
                        textMuted = textMuted
                    )
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(24.dp)
                            .background(border)
                    )
                    StatBlock(
                        label = stringResource(id = R.string.history_label_price),
                        value = item.price,
                        valueColor = textPrimary,
                        textMuted = textMuted
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(border)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = null,
                    tint = textMuted,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                    color = textMuted
                )
            }
            Row(
                modifier = Modifier.clickable(onClick = onViewDetails),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.history_view_details),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.proplens_primary_blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = colorResource(id = R.color.proplens_primary_blue),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun StatusBadge(
    text: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.1f))
            .border(width = 1.dp, color = color.copy(alpha = 0.2f), shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = color
        )
    }
}

@Composable
private fun StatBlock(
    label: String,
    value: String,
    valueColor: Color,
    textMuted: Color
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = textMuted
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = valueColor
        )
    }
}

@Composable
private fun HistoryBottomNav(
    primary: Color,
    muted: Color,
    border: Color,
    onAction: (HistoryAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = border)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HistoryNavItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Home,
                label = stringResource(id = R.string.home_bottom_home),
                selected = false,
                primary = primary,
                muted = muted,
                onClick = { onAction(HistoryAction.NavHome) }
            )
            HistoryNavItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.History,
                label = stringResource(id = R.string.history_nav_history),
                selected = true,
                primary = primary,
                muted = muted,
                onClick = { onAction(HistoryAction.NavHistory) }
            )
            HistoryNavItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Analytics,
                label = stringResource(id = R.string.profile_nav_analyze),
                selected = false,
                primary = primary,
                muted = muted,
                onClick = { onAction(HistoryAction.NavAnalyze) }
            )
            HistoryNavItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Person,
                label = stringResource(id = R.string.profile_nav_profile),
                selected = false,
                primary = primary,
                muted = muted,
                onClick = { onAction(HistoryAction.NavProfile) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun HistoryNavItem(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    primary: Color,
    muted: Color,
    onClick: () -> Unit
) {
    val contentColor = if (selected) primary else muted
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            ),
            color = contentColor,
            textAlign = TextAlign.Center
        )
    }
}
