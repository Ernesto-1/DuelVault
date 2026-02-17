package com.myapp.duelvault.utils.components

import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun PullRefresh(
    modifier: Modifier,
    isRefreshing: MutableState<Boolean>,
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = isRefreshing.value,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        contentAlignment = Alignment.TopCenter
    ) {
        content()
    }
}