package com.myapp.duelvault.onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.duelvault.R
import com.myapp.duelvault.onboarding.domain.WelcomeEvent
import com.myapp.duelvault.onboarding.domain.WelcomeViewModel
import com.myapp.duelvault.utils.navigation.AppDestination

@Composable
fun Welcome(onNavigate: (AppDestination) -> Unit, viewModel: WelcomeViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    var name by remember { mutableStateOf("") }

    LaunchedEffect(key1 = state.isSuccess) {
        if (state.isSuccess) {
            onNavigate.invoke(AppDestination.DashboardGraph)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dv_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Text("¡Bienvenido!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("¿Cómo te llamas?", style = MaterialTheme.typography.bodyLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { nuevoTexto ->
                if (nuevoTexto.length <= 20) {
                    name = nuevoTexto
                }
            },
            label = { Text("Tu nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            singleLine = true
        )

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    viewModel.onEvent(WelcomeEvent.SaveName(name))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank()
        ) {
            Text("Empezar")
        }
    }
}