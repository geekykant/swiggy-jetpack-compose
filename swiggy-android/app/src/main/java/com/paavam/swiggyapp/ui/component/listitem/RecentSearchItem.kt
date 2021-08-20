package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.utils.noRippleClickable

@Composable
fun RecentSearchItem(searchItem: String, onClick: (searchItem: String) -> Unit) {
    val textColor = Color.Black.copy(0.35f)
    Row(
        modifier = Modifier
            .noRippleClickable { onClick(searchItem) }
            .padding(horizontal = 20.dp),
    ) {
        Icon(
            painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 5.dp),
            tint = textColor
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = searchItem,
                color = textColor,
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .fillMaxHeight(),
                fontWeight = FontWeight.Normal,
                fontSize = 19.sp
            )
            Divider(thickness = 0.5.dp)
        }
    }
}