package com.mybudget.app.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalOffer
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.mybudget.app.feature.category.presentation.CategoryScreen
import com.mybudget.app.feature.dashboard.presentation.DashboardScreen
import com.mybudget.app.feature.settings.presentation.BankAppSelectionScreen
import com.mybudget.app.feature.settings.presentation.SettingsScreen
import com.mybudget.app.feature.transaction.presentation.TransactionScreen

private data class TopLevelDestination(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

private val destinations = listOf(
    TopLevelDestination(
        route = AppRoutes.DASHBOARD,
        label = "Home",
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    TopLevelDestination(
        route = AppRoutes.CATEGORIES,
        label = "Categories",
        selectedIcon = Icons.Rounded.LocalOffer,
        unselectedIcon = Icons.Outlined.LocalOffer
    ),
    TopLevelDestination(
        route = AppRoutes.TRANSACTIONS,
        label = "Transactions",
        selectedIcon = Icons.Rounded.History,
        unselectedIcon = Icons.Outlined.History
    ),
    TopLevelDestination(
        route = AppRoutes.SETTINGS,
        label = "Settings",
        selectedIcon = Icons.Rounded.Tune,
        unselectedIcon = Icons.Outlined.Tune
    )
)

@Composable
fun MyBudgetApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            if (currentDestination?.route != AppRoutes.BANK_APP_SELECTION) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    tonalElevation = 0.dp
                ) {
                    destinations.forEach { destination ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == destination.route
                        } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    navController.navigate(destination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) {
                                        destination.selectedIcon
                                    } else {
                                        destination.unselectedIcon
                                    },
                                    contentDescription = destination.label
                                )
                            },
                            label = { Text(destination.label) },
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
//        val isBankAppSelection =
//            currentDestination?.route == AppRoutes.BANK_APP_SELECTION
        NavHost(
            navController = navController,
            startDestination = AppRoutes.DASHBOARD,
            modifier =                 Modifier.padding(innerPadding),
                    enterTransition = {
                if (targetState.destination.route == AppRoutes.BANK_APP_SELECTION) {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    )
                } else {
                    EnterTransition.None
                }
            },
            exitTransition = {
                if (targetState.destination.route == AppRoutes.BANK_APP_SELECTION) {
                    slideOutHorizontally(
                        targetOffsetX = { -it / 4 },
                        animationSpec = tween(300)
                    )
                } else {
                    ExitTransition.None
                }
            },
            popEnterTransition = {
                if (initialState.destination.route == AppRoutes.BANK_APP_SELECTION) {
                    slideInHorizontally(
                        initialOffsetX = { -it / 4 },
                        animationSpec = tween(300)
                    )
                } else {
                    EnterTransition.None
                }
            },
            popExitTransition = {
                if (initialState.destination.route == AppRoutes.BANK_APP_SELECTION) {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    )
                } else {
                    ExitTransition.None
                }
            }
        ) {
            composable(AppRoutes.DASHBOARD) { DashboardScreen() }
            composable(AppRoutes.CATEGORIES) { CategoryScreen() }
            composable(AppRoutes.TRANSACTIONS) { TransactionScreen() }
            composable(AppRoutes.SETTINGS) {
                SettingsScreen(
                    onManageBankApps = { navController.navigate(AppRoutes.BANK_APP_SELECTION) }
                )
            }
            composable(AppRoutes.BANK_APP_SELECTION) {
                BankAppSelectionScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
