package com.example.swiggyapp.ui.cart

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
import com.example.swiggyapp.R
import com.example.swiggyapp.ui.theme.SwiggyTheme
import com.example.swiggyapp.ui.theme.Typography
import java.util.*

@Composable
fun CartScreen(
    outerPaddingValues: PaddingValues
) {
    NoItemsInCart(outerPaddingValues)
}

@Composable
fun NoItemsInCart(
    outerPaddingValues: PaddingValues
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
            onClick = { /* Open home page */ },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primary
            ),
            contentPadding = PaddingValues(horizontal = 10.dp),
            border= BorderStroke(ButtonDefaults.OutlinedBorderSize, MaterialTheme.colors.primary),
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
        CartScreen(outerPaddingValues = PaddingValues(10.dp))
    }
}
