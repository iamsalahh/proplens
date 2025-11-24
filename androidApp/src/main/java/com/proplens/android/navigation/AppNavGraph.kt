package com.proplens.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proplens.android.di.AppContainer
import com.proplens.android.ui.adddeal.AddDealScreen
import com.proplens.android.ui.home.HomeScreen
import com.proplens.android.ui.inputmode.InputModeScreen
import com.proplens.android.viewmodel.AddDealViewModel
import com.proplens.android.viewmodel.HomeViewModel

@Composable
fun AppNavGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME,
        modifier = modifier
    ) {
        composable(AppDestinations.HOME) {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(appContainer))
            HomeScreen(
                state = homeViewModel.state,
                onAddDeal = { navController.navigate(AppDestinations.INPUT_MODE) },
                onDealSelected = { dealId -> navController.navigate("deal_details/$dealId") }
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
    }
}
