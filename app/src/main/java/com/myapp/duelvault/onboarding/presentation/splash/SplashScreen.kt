package com.myapp.duelvault.onboarding.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.duelvault.R
import com.myapp.duelvault.onboarding.domain.splash.SplashViewModel
import com.myapp.duelvault.utils.navigation.AppDestination
import com.myapp.duelvault.utils.theme.backgroud
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate: (AppDestination) -> Unit, viewModel: SplashViewModel = hiltViewModel()){
    val userName = viewModel.userName.collectAsStateWithLifecycle().value
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(userName) {
        delay(2000)
        if (userName.isNotBlank()) {
            onNavigate.invoke(AppDestination.DashboardGraph)
        }else{
            onNavigate.invoke(AppDestination.Welcome)
        }
    }

    LaunchedEffect(key1 = Unit) {
        startAnimation = true
    }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500
        ),
        label = "Splash Alpha Animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroud),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnimation.value)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dv_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
}