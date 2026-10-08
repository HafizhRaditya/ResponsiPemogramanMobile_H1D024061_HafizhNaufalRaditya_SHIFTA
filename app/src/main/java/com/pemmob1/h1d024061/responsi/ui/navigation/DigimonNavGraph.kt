package com.pemmob1.h1d024061.responsi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pemmob1.h1d024061.responsi.ui.screen.DetailScreen
import com.pemmob1.h1d024061.responsi.ui.screen.HomeScreen

object Routes {
    const val HOME = "home"
    const val DIGIMON_ID = "digimonId"
    const val DETAIL = "detail/{$DIGIMON_ID}"

    fun detail(id: Int): String = "detail/$id"
}

@Composable
fun DigimonNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier,
    ) {
        composable(route = Routes.HOME) {
            HomeScreen(
                onDigimonClick = { id -> navController.navigate(Routes.detail(id)) },
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument(Routes.DIGIMON_ID) { type = NavType.IntType },
            ),
        ) {
            DetailScreen(
                onBack = { navController.popBackStack(Routes.HOME, inclusive = false) },
            )
        }
    }
}
