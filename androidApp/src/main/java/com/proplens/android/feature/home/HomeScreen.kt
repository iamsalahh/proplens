package com.proplens.android.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.proplens.android.R

@Composable
fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = colorResource(id = R.color.proplens_primary_blue)
    val background = Color.White
    val surface = colorResource(id = R.color.proplens_card_surface)
    val textPrimary = colorResource(id = R.color.proplens_text_primary)
    val textMuted = colorResource(id = R.color.proplens_text_muted)
    val border = colorResource(id = R.color.proplens_border_light)
    val success = colorResource(id = R.color.proplens_positive_green)

    Scaffold(
        modifier = modifier,
        containerColor = background,
        bottomBar = {
            ProplensBottomNav(
                selectedTab = state.selectedTab,
                onTabSelected = { onAction(HomeAction.BottomNavSelected(it)) },
                primary = primary,
                muted = textMuted
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 28.dp)
        ) {
            item {
                HeaderBar(
                    primary = primary,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    onProfile = { onAction(HomeAction.BottomNavSelected(HomeTab.Profile)) },
                    onNotifications = { onAction(HomeAction.HistoryClicked) }
                )
            }
            item {
                LatestVerdictRow(
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    primary = primary,
                    onClick = { onAction(HomeAction.HistoryClicked) }
                )
            }
            item {
                VerdictCard(
                    data = state.lastAnalysis,
                    primary = primary,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    success = success,
                    onClick = { onAction(HomeAction.ViewReportClicked) }
                )
            }
            item {
                StartNewAnalysisHeader(
                    textPrimary = textPrimary,
                    textMuted = textMuted
                )
            }
            items(state.startActions) { action ->
                val isHighlight = action.badge != null
                ActionCard(
                    title = action.title,
                    subtitle = action.subtitle,
                    icon = action.icon,
                    badge = action.badge,
                    primary = primary,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    highlighted = isHighlight,
                    onClick = {
                        when (action.id) {
                            "manual_entry" -> onAction(HomeAction.ManualEntryClicked)
                            "paste_url" -> onAction(HomeAction.PasteUrlClicked)
                            "upload_screenshot" -> onAction(HomeAction.UploadScreenshotClicked)
                        }
                    }
                )
            }
            item {
                RecentHistorySection(
                    items = state.recentHistory,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    primary = primary,
                    onViewAll = { onAction(HomeAction.HistoryListClicked) },
                    onItemClick = { onAction(HomeAction.HistoryItemClicked(it)) }
                )
            }
        }
    }
}

@Composable
private fun HeaderBar(
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    onProfile: () -> Unit,
    onNotifications: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(primary.copy(alpha = 0.15f), backgroundGradient(primary))
                        )
                    )
                    .border(width = 1.dp, color = primary.copy(alpha = 0.15f), shape = CircleShape)
                    .clickable(onClick = onProfile),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = textPrimary
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.home_welcome_back),
                    style = MaterialTheme.typography.bodySmall,
                    color = textMuted
                )
                Text(
                    text = stringResource(id = R.string.home_user_role),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = textPrimary
                )
            }
        }
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.proplens_card_surface))
                .border(width = 1.dp, color = border, shape = CircleShape)
                .clickable(onClick = onNotifications),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = textMuted
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 10.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEF4444))
            )
        }
    }
}

@Composable
private fun LatestVerdictRow(
    textPrimary: Color,
    textMuted: Color,
    primary: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(primary)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.home_latest_verdict),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = textPrimary
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.home_just_now),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = textMuted
        )
    }
}

@Composable
private fun VerdictCard(
    data: LastAnalysisUiModel,
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    success: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(24.dp))
            .background(colorResource(id = R.color.proplens_card_surface))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .background(
                    Brush.linearGradient(
                        colors = listOf(primary.copy(alpha = 0.08f), Color.White),
                        start = Offset.Zero,
                        end = Offset(800f, 800f)
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = textPrimary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = null,
                            tint = textMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = data.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = textMuted
                        )
                    }
                }
                TextTag(
                    text = data.badge.uppercase(),
                    background = success,
                    contentColor = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatBlock(
                    label = stringResource(id = R.string.home_proj_yield),
                    value = data.projYield,
                    valueColor = success,
                    textMuted = textMuted
                )
                Divider(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp),
                    color = border
                )
                StatBlock(
                    label = stringResource(id = R.string.home_fair_price),
                    value = data.fairPrice,
                    valueColor = textPrimary,
                    textMuted = textMuted
                )
                Divider(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp),
                    color = border
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(id = R.string.home_ai_score),
                        style = MaterialTheme.typography.labelMedium,
                        color = textMuted
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = data.aiScore.substringBefore("/"),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = primary
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "/${data.aiScore.substringAfter("/")}",
                            style = MaterialTheme.typography.labelSmall,
                            color = textMuted
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .background(primary.copy(alpha = 0.08f))
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.home_view_full_analysis),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = primary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun StatBlock(
    label: String,
    value: String,
    valueColor: Color,
    textMuted: Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = textMuted
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = valueColor
        )
    }
}

@Composable
private fun StartNewAnalysisHeader(
    textPrimary: Color,
    textMuted: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.home_start_new_analysis),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = textPrimary
        )
        Text(
            text = stringResource(id = R.string.home_start_new_analysis_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = textMuted
        )
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badge: String?,
    primary: Color,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    highlighted: Boolean,
    onClick: () -> Unit
    ) {
        val background = if (highlighted) {
            Brush.horizontalGradient(
                colors = listOf(primary.copy(alpha = 0.07f), Color.White)
            )
    } else {
        null
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(
                width = 1.dp,
                color = if (highlighted) primary.copy(alpha = 0.2f) else border,
                shape = RoundedCornerShape(18.dp)
            )
            .let { base ->
                if (background != null) {
                    base.background(background)
                } else {
                    base.background(colorResource(id = R.color.proplens_card_surface))
                }
            }
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(if (highlighted) primary else Color.White)
                    .border(
                        width = 1.dp,
                        color = if (highlighted) primary.copy(alpha = 0.4f) else border,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (highlighted) Color.White else primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = textPrimary
                    )
                    if (badge != null) {
                        TextTag(
                            text = badge.uppercase(),
                            background = Color.White,
                            contentColor = primary
                        )
                    }
                }
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = textMuted
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = if (highlighted) primary else textMuted
            )
        }
    }
}

@Composable
private fun RecentHistorySection(
    items: List<RecentHistoryItem>,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    primary: Color,
    onViewAll: () -> Unit,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.home_recent_history),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = textPrimary
            )
            Text(
                text = stringResource(id = R.string.home_view_all),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = primary,
                modifier = Modifier.clickable(onClick = onViewAll)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items.forEach { item ->
                RecentHistoryCard(
                    item = item,
                    textPrimary = textPrimary,
                    textMuted = textMuted,
                    border = border,
                    primary = primary,
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}

@Composable
private fun RecentHistoryCard(
    item: RecentHistoryItem,
    textPrimary: Color,
    textMuted: Color,
    border: Color,
    primary: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(primary.copy(alpha = 0.4f), primary.copy(alpha = 0.8f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Place,
                contentDescription = null,
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Place,
                    contentDescription = null,
                    tint = textMuted,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = item.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = textMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TextTag(
                text = item.netYield,
                background = primary.copy(alpha = 0.1f),
                contentColor = primary
            )
            Text(
                text = item.price,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = textPrimary
            )
        }
    }
}

@Composable
private fun TextTag(
    text: String,
    background: Color,
    contentColor: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(background)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = contentColor
        )
    }
}

@Composable
private fun ProplensBottomNav(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    primary: Color,
    muted: Color
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            HomeTab.Home to Icons.Filled.Home,
            HomeTab.Saved to Icons.Outlined.BookmarkBorder,
            HomeTab.Profile to Icons.Outlined.Person
        )
        items.forEach { (tab, icon) ->
            val selected = tab == selectedTab
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = tab.name
                    )
                },
                label = {
                    val label = when (tab) {
                        HomeTab.Home -> stringResource(id = R.string.home_bottom_home)
                        HomeTab.Saved -> stringResource(id = R.string.home_bottom_saved)
                        HomeTab.Profile -> stringResource(id = R.string.home_bottom_profile)
                    }
                    Text(text = label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primary,
                    selectedTextColor = primary,
                    unselectedIconColor = muted,
                    unselectedTextColor = muted,
                    indicatorColor = primary.copy(alpha = 0.12f)
                )
            )
        }
    }
}

private fun backgroundGradient(primary: Color): Color = primary.copy(alpha = 0.05f)
