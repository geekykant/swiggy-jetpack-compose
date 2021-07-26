package com.example.swiggyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            CartScreen()
        }
        composable(ScreenItem.Account.route) {
            AccountScreen()
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

    var selectedRoute by remember { mutableStateOf(ScreenItem.Home.route) }
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = MaterialTheme.colors.primaryVariant,
        elevation = 8.dp
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen.route) {
                        selectedRoute -> Icon(
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
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                unselectedContentColor = Color.Black.copy(0.65f),
                alwaysShowLabel = true,
                selected = selectedRoute == screen.route, //currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    selectedRoute = screen.route
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
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