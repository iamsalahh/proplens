package com.proplens.android.feature.home

import androidx.compose.ui.graphics.vector.ImageVector

data class HomeUiState(
    val lastAnalysis: LastAnalysisUiModel,
    val startActions: List<StartActionUiModel>,
    val recentHistory: List<RecentHistoryItem>,
    val selectedTab: HomeTab
)

data class LastAnalysisUiModel(
    val timeAgo: String,
    val title: String,
    val location: String,
    val badge: String,
    val projYield: String,
    val fairPrice: String,
    val aiScore: String
)

data class StartActionUiModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val badge: String? = null
)

data class RecentHistoryItem(
    val id: String,
    val title: String,
    val location: String,
    val netYield: String,
    val price: String
)

enum class HomeTab { Home, Saved, Profile }

sealed interface HomeAction {
    data object HistoryClicked : HomeAction
    data object ViewReportClicked : HomeAction
    data object PasteUrlClicked : HomeAction
    data object UploadScreenshotClicked : HomeAction
    data object ManualEntryClicked : HomeAction
    data object HistoryListClicked : HomeAction
    data class HistoryItemClicked(val id: String) : HomeAction
    data class BottomNavSelected(val tab: HomeTab) : HomeAction
}
