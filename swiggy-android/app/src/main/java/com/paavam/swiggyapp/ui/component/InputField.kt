package com.paavam.swiggyapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.theme.Prox

@Composable
fun InputMessageField(
    inputText: String,
    onTextChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
) {
    val searchTextStyle = TextStyle(
        fontFamily = Prox,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 15.dp)
    ) {
        Icon(
            painterResource(id = R.drawable.ic_list),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        TextField(
            value = inputText,
            onValueChange = { onTextChange(it) },
            modifier = Modifier,
            placeholder = {
                Text(
                    placeholderText,
                    modifier = Modifier.fillMaxHeight(),
                    textAlign = TextAlign.Center,
                    style = searchTextStyle,
                    maxLines = 1
                )
            },
            textStyle = searchTextStyle,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            maxLines = 1
        )
    }
}