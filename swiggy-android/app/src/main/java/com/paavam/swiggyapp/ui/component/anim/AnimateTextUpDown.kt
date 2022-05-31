package com.paavam.swiggyapp.ui.component.anim

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateTextUpDown(message: String, maxLines: Int, modifier: Modifier = Modifier) {
    AnimatedContent(targetState = message,
        transitionSpec = {
            if (targetState > initialState) {
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height } + fadeOut()
            }.using(SizeTransform(clip = false))
        }
    ) {
        Text(
            text = message,
            modifier = modifier
                .padding(horizontal = 5.dp)
                .sizeIn(maxHeight = 30.dp),
            maxLines = maxLines
        )
    }
}