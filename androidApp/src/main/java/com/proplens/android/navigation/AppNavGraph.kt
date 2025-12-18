package com.proplens.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proplens.android.di.AppContainer
import com.proplens.android.ui.adddeal.AddDealScreen
import com.proplens.android.ui.inputmode.InputModeScreen
import com.proplens.android.ui.onboarding.OnboardingRoute
import com.proplens.android.viewmodel.AddDealViewModel
import com.proplens.android.feature.home.HomeRoute
import com.proplens.android.feature.upload.UploadScreenshotRoute
import com.proplens.android.feature.paste.PasteListingRoute
import com.proplens.android.feature.manual.ManualEntryRoute
import com.proplens.android.feature.manual.ManualEntryDetailsRoute
import com.proplens.android.feature.manual.ManualEntryReviewRoute
import com.proplens.android.feature.analysis.AnalysisRoute
import com.proplens.android.feature.transactions.TransactionsRoute
import com.proplens.android.feature.profile.ProfileRoute
import com.proplens.android.feature.history.HistoryRoute

@Composable
fun AppNavGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.ONBOARDING,
        modifier = modifier
    ) {
        composable(AppDestinations.ONBOARDING) {
            OnboardingRoute(
                onFinished = {
                    navController.navigate(AppDestinations.HOME) {
                        popUpTo(AppDestinations.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(AppDestinations.HOME) {
            HomeRoute(
                onAction = { action ->
                    when (action) {
                        com.proplens.android.feature.home.HomeAction.UploadScreenshotClicked -> {
                            navController.navigate(AppDestinations.UPLOAD_SCREENSHOT)
                        }
                        com.proplens.android.feature.home.HomeAction.PasteUrlClicked -> {
                            navController.navigate(AppDestinations.PASTE_LISTING)
                        }
                        com.proplens.android.feature.home.HomeAction.ManualEntryClicked -> {
                            navController.navigate(AppDestinations.MANUAL_ENTRY)
                        }
                        com.proplens.android.feature.home.HomeAction.HistoryClicked,
                        com.proplens.android.feature.home.HomeAction.ViewReportClicked,
                        is com.proplens.android.feature.home.HomeAction.HistoryItemClicked -> {
                            navController.navigate(AppDestinations.ANALYSIS)
                        }
                        is com.proplens.android.feature.home.HomeAction.BottomNavSelected -> {
                            if (action.tab == com.proplens.android.feature.home.HomeTab.Profile) {
                                navController.navigate(AppDestinations.PROFILE)
                            } else if (action.tab == com.proplens.android.feature.home.HomeTab.Saved) {
                                navController.navigate(AppDestinations.HISTORY)
                            }
                        }
                        else -> Unit
                    }
                }
            )
        }

        composable(AppDestinations.INPUT_MODE) {
            InputModeScreen(
                onManualSelected = { navController.navigate(AppDestinations.ADD_DEAL) },
                onScreenshotSelected = { navController.navigate(AppDestinations.ADD_DEAL) },
                onUrlSelected = { navController.navigate(AppDestinations.ADD_DEAL) }
            )
        }

        composable(AppDestinations.ADD_DEAL) {
            val addDealViewModel: AddDealViewModel = viewModel(factory = AddDealViewModel.provideFactory(appContainer))
            AddDealScreen(
                state = addDealViewModel.state,
                onEvent = addDealViewModel::onEvent,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestinations.UPLOAD_SCREENSHOT) {
            UploadScreenshotRoute(
                onAction = { action ->
                    when (action) {
                        com.proplens.android.feature.upload.UploadScreenshotAction.Close,
                        com.proplens.android.feature.upload.UploadScreenshotAction.ScanAndPrefill,
                        com.proplens.android.feature.upload.UploadScreenshotAction.EnterManually -> navController.popBackStack()
                        else -> Unit
                    }
                }
            )
        }

        composable(AppDestinations.PASTE_LISTING) {
            PasteListingRoute(
                onAction = { action ->
                    when (action) {
                        com.proplens.android.feature.paste.PasteListingAction.Close,
                        com.proplens.android.feature.paste.PasteListingAction.UseDetected,
                        com.proplens.android.feature.paste.PasteListingAction.Extract -> navController.popBackStack()
                        else -> Unit
                    }
                }
            )
        }

        composable(AppDestinations.MANUAL_ENTRY) {
            ManualEntryRoute(
                onClose = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onContinue = { navController.navigate(AppDestinations.MANUAL_ENTRY_DETAILS) }
            )
        }

        composable(AppDestinations.MANUAL_ENTRY_DETAILS) {
            ManualEntryDetailsRoute(
                onBack = { navController.popBackStack() },
                onSkip = { navController.navigate(AppDestinations.MANUAL_ENTRY_REVIEW) },
                onContinue = { navController.navigate(AppDestinations.MANUAL_ENTRY_REVIEW) }
            )
        }

        composable(AppDestinations.MANUAL_ENTRY_REVIEW) {
            ManualEntryReviewRoute(
                onBack = { navController.popBackStack() },
                onAnalyze = { navController.navigate(AppDestinations.ANALYSIS) }
            )
        }

        composable(AppDestinations.ANALYSIS) {
            AnalysisRoute(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
                onOpenTransactions = { navController.navigate(AppDestinations.TRANSACTIONS) }
            )
        }

        composable(AppDestinations.TRANSACTIONS) {
            TransactionsRoute(onBack = { navController.popBackStack() })
        }

        composable(AppDestinations.PROFILE) {
            ProfileRoute(
                onAction = { action ->
                    when (action) {
                        com.proplens.android.feature.profile.ProfileAction.NavDeals -> navController.navigate(AppDestinations.HOME)
                        com.proplens.android.feature.profile.ProfileAction.NavSaved -> navController.navigate(AppDestinations.HISTORY)
                        com.proplens.android.feature.profile.ProfileAction.NavAnalyze -> navController.navigate(AppDestinations.ANALYSIS)
                        com.proplens.android.feature.profile.ProfileAction.NavProfile -> Unit
                        com.proplens.android.feature.profile.ProfileAction.Back -> navController.popBackStack()
                        else -> Unit
                    }
                }
            )
        }

        composable(AppDestinations.HISTORY) {
            HistoryRoute(
                onAction = { action ->
                    when (action) {
                        com.proplens.android.feature.history.HistoryAction.NavHome -> navController.navigate(AppDestinations.HOME)
                        com.proplens.android.feature.history.HistoryAction.NavHistory -> Unit
                        com.proplens.android.feature.history.HistoryAction.NavAnalyze -> navController.navigate(AppDestinations.ANALYSIS)
                        com.proplens.android.feature.history.HistoryAction.NavProfile -> navController.navigate(AppDestinations.PROFILE)
                        com.proplens.android.feature.history.HistoryAction.ViewDetails -> navController.navigate(AppDestinations.ANALYSIS)
                        com.proplens.android.feature.history.HistoryAction.AddNew -> navController.navigate(AppDestinations.MANUAL_ENTRY)
                        com.proplens.android.feature.history.HistoryAction.Back -> navController.popBackStack()
                        else -> Unit
                    }
                }
            )
        }
    }
}
