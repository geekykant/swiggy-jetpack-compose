package com.paavam.swiggyapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.paavam.swiggyapp.core.data.model.UserProfile
import com.paavam.swiggyapp.core.ui.UiState
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.addTopBarBottomLine
import com.paavam.swiggyapp.viewmodel.AccountViewModel
import kotlin.math.min

@ExperimentalFoundationApi
@Composable
fun AccountScreen(
    outerPadding: PaddingValues
) {
    val viewModel: AccountViewModel = hiltViewModel()
    val userDetailsState = viewModel.state.collectAsState()

    ProvideWindowInsets {
        when (userDetailsState.value.uiLoadingState) {
            is UiState.Loading -> LoadingAccountScreen()
            is UiState.Failed -> ErrorAccountScreen(
                outerPaddingValues = outerPadding,
                onRetryClick = {
                    /* Check for internet/error again */
                })
            is UiState.Success -> userDetailsState.value.user?.let {
                SuccessAccountScreen(
                    user = it,
                    outerPaddingValues = outerPadding
                )
            }
        }
    }
}

@Composable
fun SuccessAccountScreen(
    user: UserProfile,
    outerPaddingValues: PaddingValues
) {
    val scrollState = rememberLazyListState()
    var isScrollStateChanged by remember { mutableStateOf(false) }
    isScrollStateChanged = scrollState.firstVisibleItemIndex != 0

    val position by animateFloatAsState(if (isScrollStateChanged) 0f else -45f)

    Box(Modifier.fillMaxSize()) {
        ShowItemsInAccount(
            user = user,
            scrollState = scrollState
        )
        AccountTopAppBar(
            modifier = Modifier
                .graphicsLayer {
                    alpha = min(1f, 1 + (position / 45f))
                    translationY = (position)
                }
                .navigationBarsPadding(bottom = false)
        )
    }
}

@Composable
fun ShowItemsInAccount(
    user: UserProfile,
    scrollState: LazyListState
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 15.dp),
        state = scrollState
    ) {
        item {
            Column(
                modifier = Modifier.padding(vertical = 15.dp)
            ) {
                Text(
                    text = user.fullName.uppercase(),
                    style = Typography.h2
                )
                Spacer(Modifier.height(10.dp))
                Row {
                    Text(text = user.mobileNo)
                    Text(
                        text = ".",
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                    Text(text = user.emailId)
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                thickness = 2.dp
            )
        }
    }
}

@Composable
fun AccountTopAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "MY ACCOUNT",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        },
        backgroundColor = Color.White,
        modifier = modifier.addTopBarBottomLine(),
        elevation = 4.dp,
    )
}

@Composable
fun ErrorAccountScreen(outerPaddingValues: PaddingValues, onRetryClick: () -> Unit) {
    //check network error
}

@Composable
fun LoadingAccountScreen() {
    //loading shimmer
}
