package com.paavam.swiggyapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.model.AddressType
import com.paavam.swiggyapp.model.SwiggyViewModel
import com.paavam.swiggyapp.model.UserAddress
import com.paavam.swiggyapp.ui.navigation.NavScreen
import com.paavam.swiggyapp.ui.navigation.Navigation
import com.paavam.swiggyapp.ui.theme.SwiggyTheme
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.noRippleClickable
import java.util.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SwiggyMain() {
    SwiggyTheme {
        val viewModel: SwiggyViewModel = hiltViewModel()
        val navController = rememberNavController()

        val sheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = { AddressPickBottomSheet(viewModel) },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = Color.White
        ) {
            Scaffold(
                topBar = { },
                bottomBar = { BottomNavigationBar(navController) }
            ) { outerPaddingValues ->
                // sets the NavHost and default start to [NavScreen.Home]
                Navigation(navController, outerPaddingValues)
            }
        }

        val askAddressModal = viewModel.askAddressModal.collectAsState()
        when (askAddressModal.value) {
            true -> LaunchedEffect(Unit) { sheetState.show() }
            false -> LaunchedEffect(Unit) { sheetState.hide() }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AddressPickBottomSheet(
    viewModel: SwiggyViewModel
) {
    Column {
        Box(
            modifier = Modifier
                .background(Color(0xFF41A0EC))
                .padding(horizontal = 15.dp, vertical = 18.dp)
                .fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_gps),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Location Permission is off",
                            style = Typography.h2,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Granting location permission will ensure accurate address and hassle free delivery",
                        style = Typography.h5,
                        color = Color.White,
                    )
                }

                Button(
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        viewModel.changeAddressSheetState(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFF7F7F7),
                        contentColor = Color(0xFF41A0EC)
                    ),
                    modifier = Modifier
                        .width(80.dp)
                        .height(30.dp)
                ) {
                    Text(
                        "GRANT",
                        style = Typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp)
        ) {
            Text(
                "Select Delivery Address",
                style = Typography.h1,
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 20.dp)
            )
            Divider(thickness = 0.75.dp)

            val addressList = viewModel.viewState.value.userAddressList
            var selectedAddress by remember { mutableStateOf<UserAddress?>(null) }

            Column(
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                PickAddressForDelivery(
                    addressList,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    selectedAddress = { address ->
                        selectedAddress = address
                    }
                )
            }

            Divider(thickness = 0.75.dp)

            Row(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                    tint = MaterialTheme.colors.primary.copy(0.9f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Enter Location Manually",
                    style = Typography.h5
                )
            }

            var isSelected by remember { mutableStateOf(false) }
            selectedAddress?.let {
                isSelected = true
            }

            Button(
                onClick = { isSelected = !isSelected },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSelected) Color.LightGray else MaterialTheme.colors.primaryVariant,
                    contentColor = if (isSelected) Color.White
                    else Color.White.copy(alpha = 0.7f)
                ),
                enabled = isSelected
            ) {
                Text(
                    "SELECT & PROCEED",
                    style = Typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(horizontal = 2.dp, vertical = 5.dp)
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun PickAddressForDelivery(
    addressList: List<UserAddress>,
    selectedAddress: (UserAddress) -> Unit,
    modifier: Modifier
) {
    addressList.forEach {
        Row(
            modifier = modifier
                .noRippleClickable { selectedAddress(it) }
        ) {
            Icon(
                if (it.type == AddressType.HOME) Icons.Outlined.Home else Icons.Outlined.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .size(26.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    it.label,
                    style = Typography.h2.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = it.fullAddress,
                    style = Typography.h5,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavScreen.Home,
        NavScreen.Search,
        NavScreen.Cart,
        NavScreen.Account
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