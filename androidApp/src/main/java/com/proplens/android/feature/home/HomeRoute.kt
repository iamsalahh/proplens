package com.proplens.android.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import com.proplens.android.R

@Composable
fun HomeRoute(
    onAction: (HomeAction) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(HomeTab.Home) }
    var placeholderTitle by remember { mutableStateOf<String?>(null) }

    val historyLabel = stringResource(id = R.string.home_history)
    val viewReportLabel = stringResource(id = R.string.home_view_full_analysis)
    val pasteUrlLabel = stringResource(id = R.string.home_action_paste_url)
    val uploadScreenshotLabel = stringResource(id = R.string.home_action_upload_screenshot)
    val manualEntryLabel = stringResource(id = R.string.home_action_manual_entry)
    val recentHistoryLabel = stringResource(id = R.string.home_recent_history)
    val bottomHomeLabel = stringResource(id = R.string.home_bottom_home)
    val bottomSavedLabel = stringResource(id = R.string.home_bottom_saved)
    val bottomProfileLabel = stringResource(id = R.string.home_bottom_profile)

    val state = HomeUiState(
        lastAnalysis = LastAnalysisUiModel(
            timeAgo = stringResource(id = R.string.home_just_now),
            title = stringResource(id = R.string.home_latest_property_title),
            location = stringResource(id = R.string.home_latest_property_location),
            badge = stringResource(id = R.string.home_badge_strong_buy),
            projYield = "7.8%",
            fairPrice = "AED 2.1M",
            aiScore = "92/100"
        ),
        startActions = listOf(
            StartActionUiModel(
                id = "manual_entry",
                title = stringResource(id = R.string.home_action_manual_entry),
                subtitle = stringResource(id = R.string.home_action_manual_entry_subtitle),
                icon = Icons.Outlined.EditNote
            ),
            StartActionUiModel(
                id = "paste_url",
                title = stringResource(id = R.string.home_action_paste_url),
                subtitle = stringResource(id = R.string.home_action_paste_url_subtitle),
                icon = Icons.Outlined.Link
            ),
            StartActionUiModel(
                id = "upload_screenshot",
                title = stringResource(id = R.string.home_action_upload_screenshot),
                subtitle = stringResource(id = R.string.home_action_upload_screenshot_subtitle),
                icon = Icons.Outlined.CameraAlt,
                badge = stringResource(id = R.string.home_action_ai_beta)
            )
        ),
        recentHistory = listOf(
            RecentHistoryItem(
                id = "history_1",
                title = "Downtown Views II",
                location = "Downtown Dubai",
                netYield = "6.2% NET",
                price = "AED 2.4M"
            ),
            RecentHistoryItem(
                id = "history_2",
                title = "Arabian Ranches III",
                location = "Dubailand",
                netYield = "5.8% NET",
                price = "AED 3.1M"
            )
        ),
        selectedTab = selectedTab
    )

    if (placeholderTitle != null) {
        PlaceholderScreen(
            title = placeholderTitle.orEmpty(),
            onBack = { placeholderTitle = null }
        )
    } else {
        HomeScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    HomeAction.HistoryClicked -> Unit
                    HomeAction.ViewReportClicked -> Unit
                    HomeAction.PasteUrlClicked -> Unit
                    HomeAction.UploadScreenshotClicked -> Unit
                    HomeAction.ManualEntryClicked -> Unit
                    is HomeAction.HistoryItemClicked -> Unit
                    HomeAction.HistoryListClicked -> placeholderTitle = recentHistoryLabel
                    is HomeAction.BottomNavSelected -> {
                        selectedTab = action.tab
                        placeholderTitle = when (action.tab) {
                            HomeTab.Home -> null
                            HomeTab.Saved -> null
                            HomeTab.Profile -> null
                        }
                    }
                }
                onAction(action)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaceholderScreen(
    title: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.placeholder_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.placeholder_body),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
