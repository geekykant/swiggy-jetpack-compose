package com.paavam.swiggyapp.ui.screens.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.navigation.NavScreen
import com.paavam.swiggyapp.ui.theme.SwiggyTheme
import com.paavam.swiggyapp.ui.theme.Typography
import java.util.*

@Composable
fun CartScreen(
    navController: NavController,
    outerPaddingValues: PaddingValues
) {
    NoItemsInCart(navController, outerPaddingValues)
}

@Composable
fun NoItemsInCart(
    navController: NavController,
    outerPaddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_food_in_cart),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(10.dp),
            contentScale = ContentScale.FillWidth
        )

        Text(
            "Good Food Is Always Cooking".uppercase(Locale.getDefault()),
            style = Typography.h4,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )

        Text(
            "Your cart is empty.\nAdd something from the menu",
            modifier = Modifier.padding(vertical = 20.dp),
            textAlign = TextAlign.Center
        )

        OutlinedButton(
            onClick = {
                /* Open home page */
                navController.navigate(NavScreen.Home.route)

            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primary
            ),
            contentPadding = PaddingValues(horizontal = 10.dp),
            border = BorderStroke(ButtonDefaults.OutlinedBorderSize, MaterialTheme.colors.primary),
        ) {
            Text(
                "Browse Restaurants".uppercase(Locale.getDefault()),
                style = Typography.body1,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }

    }
}

@Preview("Search Area", showBackground = true)
@Composable
fun CartScreenPreview() {
    SwiggyTheme {
        CartScreen(rememberNavController(), outerPaddingValues = PaddingValues(10.dp))
    }
}
