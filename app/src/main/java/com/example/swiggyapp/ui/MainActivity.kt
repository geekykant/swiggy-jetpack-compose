package com.example.swiggyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swiggyapp.ui.account.AccountScreen
import com.example.swiggyapp.ui.cart.CartScreen
import com.example.swiggyapp.ui.home.MainContent
import com.example.swiggyapp.ui.search.SearchScreen
import com.example.swiggyapp.ui.theme.SwiggyTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwiggyTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { },
                    bottomBar = { BottomNavigationBar(navController) }
                ) { outerPaddingValues ->
                    Navigation(navController, outerPaddingValues)
                }
            }
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    outerPaddingValues: PaddingValues
) {
    NavHost(navController, startDestination = ScreenItem.Home.route) {
        composable(ScreenItem.Home.route) {
            MainContent(outerPaddingValues)
        }
        composable(ScreenItem.Search.route) {
            SearchScreen(outerPaddingValues)
        }
        composable(ScreenItem.Cart.route) {
            CartScreen(navController, outerPaddingValues)
        }
        composable(ScreenItem.Account.route) {
            AccountScreen(outerPaddingValues)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        ScreenItem.Home,
        ScreenItem.Search,
        ScreenItem.Cart,
        ScreenItem.Account
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = MaterialTheme.colors.primary,
        elevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            val isSelectedRoute = currentDestination?.hierarchy?.any { it.route == screen.route }
            BottomNavigationItem(
                icon = {
                    when (isSelectedRoute) {
                        true -> Icon(
                            painterResource(id = screen.icon_pressed),
                            contentDescription = screen.title
                        )
                        else -> Icon(
                            painterResource(id = screen.icon),
                            contentDescription = screen.title
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.title.uppercase(Locale.getDefault()),
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Black.copy(0.65f),
                alwaysShowLabel = true,
                selected = isSelectedRoute == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SwiggyTheme {
//        MainContent(PaddingValues(10.dp))
//    }
//}