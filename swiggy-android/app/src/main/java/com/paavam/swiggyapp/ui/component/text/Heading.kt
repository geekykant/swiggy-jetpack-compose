package com.paavam.swiggyapp.ui.component.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.noRippleClickable
import java.util.*

@Composable
fun SectionHeading(
    title: String,
    tagline: String? = null,
    iconResId: Int? = null,
    showSeeAllIcon: Boolean = false,
    showSeeAllText: Boolean = false,
    seeAllText: String = "See All",
    seeAllTextColor: Color = Color.Black,
    seeAllOnClick: () -> Unit = { },
    paddingValues: PaddingValues = PaddingValues(15.dp, 8.dp)
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                iconResId?.let {
                    Image(
                        painter = painterResource(iconResId),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(2.dp)
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .padding(0.dp)
                        .alignByBaseline()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            ) {
                if (showSeeAllText) {
                    Text(
                        text = seeAllText.uppercase(Locale.getDefault()),
                        style = if (showSeeAllIcon) Typography.h3 else Typography.body1,
                        fontWeight = if (showSeeAllIcon) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .noRippleClickable { seeAllOnClick() }
                            .padding(5.dp, 0.dp)
                            .align(if (showSeeAllIcon) Alignment.CenterVertically else Alignment.Bottom),
                        color = seeAllTextColor,
                    )
                }

                if (showSeeAllIcon) {
                    Image(
                        painter = painterResource(R.drawable.ic_see_all),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }

        if (tagline != null) {
            Text(
                text = tagline,
                color = Color(0xD9000000),
                style = MaterialTheme.typography.h3
            )
        }
    }
}