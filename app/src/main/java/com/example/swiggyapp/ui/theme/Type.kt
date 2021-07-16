package com.example.swiggyapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.swiggyapp.R

// Set of Material typography styles to start with
val Prox = FontFamily(
        Font(R.font.prox_regular),
        Font(R.font.prox_semibold, FontWeight.SemiBold),
        Font(R.font.prox_bold, FontWeight.Bold),
        Font(R.font.prox_ebold, FontWeight.ExtraBold)
)

val Typography = Typography(
        defaultFontFamily = Prox,
        body1 = TextStyle(
                fontFamily = Prox,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
        ),
        h1 = TextStyle(
                fontFamily = Prox,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
        ),
        h2 = TextStyle(
                fontFamily = Prox,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
        )

        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)