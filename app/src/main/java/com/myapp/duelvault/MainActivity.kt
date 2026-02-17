package com.myapp.duelvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.myapp.duelvault.home.presentation.Home
import com.myapp.duelvault.home.presentation.detail.DetailCard
import com.myapp.duelvault.home.presentation.favorite.FavoriteCards
import com.myapp.duelvault.utils.navigation.AppDestination
import com.myapp.duelvault.utils.theme.DuelVaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuelVaultTheme {
                val navigationController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { innerPadding ->
                    NavHost(
                        navController = navigationController,
                        startDestination = AppDestination.DashboardGraph::class,
                        Modifier.padding(innerPadding)
                    ) {
                        navigation(
                            startDestination = AppDestination.Home,
                            route = AppDestination.DashboardGraph::class
                        ) {
                            composable<AppDestination.Home> {
                                Home(goNav = {
                                    navigationController.navigate(it)
                                }
                                )
                            }
                            composable<AppDestination.Detail> {
                                DetailCard(onBack = {
                                    navigationController.navigateUp()
                                })
                            }
                            composable<AppDestination.Favorite> {
                                FavoriteCards(goNav = {
                                    navigationController.navigate(it)
                                }, onBack = {
                                    navigationController.navigateUp()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}