package com.paavam.swiggyapp.lib

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.core.data.user.model.AddressType
import com.paavam.swiggyapp.core.data.user.model.UserAddress
import com.paavam.swiggyapp.ui.theme.Typography

@Composable
fun AddressChooserRadio(
    address: UserAddress,
    selected: Boolean,
    onClick: (() -> Unit),
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val borderShape = RoundedCornerShape(5.dp)

    val selectableModifier = modifier
        .selectable(
            selected = selected,
            onClick = onClick,
            enabled = true,
            role = Role.RadioButton,
            interactionSource = interactionSource,
            indication = null
        )
        .run {
            if (selected) {
                border(
                    BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),
                    borderShape
                )
            } else this
        }
        .run {
            if (selected)
                background(MaterialTheme.colors.primaryVariant.copy(alpha = 0.03f), borderShape)
            else this
        }
        .padding(horizontal = 12.dp, vertical = 15.dp)

    Row(
        modifier = selectableModifier
    ) {
        Icon(
            when (address.type) {
                AddressType.HOME -> if (selected) Icons.Filled.Home else Icons.Outlined.Home
                else -> if (selected) Icons.Filled.LocationOn else Icons.Outlined.LocationOn
            },
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .size(26.dp),
            tint = if (selected) MaterialTheme.colors.primaryVariant else Color.Black
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                address.friendlyLabel,
                style = Typography.h2,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold
            )
            Text(
                text = address.fullAddress,
                style = Typography.h5,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}