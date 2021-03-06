package com.paavam.swiggyapp.ui.component

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
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.theme.Typography
import java.util.*

@Composable
fun PlaceholderMessageOutlinedButtonScreen(
    title: String,
    subHeading: String,
    buttonText: String,
    outlinedButtonClick: () -> Unit,
    outerPaddingValues: PaddingValues,
    modifier: Modifier = Modifier
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
            title.uppercase(Locale.getDefault()),
            style = Typography.h4,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )

        Text(
            subHeading,
            modifier = Modifier.padding(vertical = 20.dp),
            textAlign = TextAlign.Center
        )

        OutlinedButton(
            onClick = outlinedButtonClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primary
            ),
            contentPadding = PaddingValues(horizontal = 10.dp),
            border = BorderStroke(
                ButtonDefaults.OutlinedBorderSize,
                MaterialTheme.colors.primary
            ),
        ) {
            Text(
                buttonText.uppercase(Locale.getDefault()),
                style = Typography.body1,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }
}