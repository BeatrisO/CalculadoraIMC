package com.example.imcumatela.presentation.imc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imcumatela.presentation.result.ImcUiEvent
import com.example.imcumatela.viewmodel.IMCViewModel

@Composable
fun IMCScreen(
    viewModel: IMCViewModel = viewModel(),
    onNavigateResultado: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

     LaunchedEffect(Unit) {
         viewModel.uiEvent.collect { event ->
             when (event) {
                 is ImcUiEvent.NavigateToResultado -> {
                     onNavigateResultado()
                 }
             }
         }
     }

     val textFieldBlackColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        cursorColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedPlaceholderColor = Color.Black,
        unfocusedPlaceholderColor = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = state.peso,
            onValueChange = viewModel::atualizarPeso,
            label = { Text("Peso (kg)") },
            placeholder = { Text("ex: 65") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = state.pesoErro,
            colors = textFieldBlackColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.altura,
            onValueChange = viewModel::atualizarAltura,
            label = { Text("Altura (cm)") },
            placeholder = { Text("ex: 170") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = state.alturaErro,
            colors = textFieldBlackColors
        )

        Spacer(modifier = Modifier.height(12.dp))

        state.mensagemErro?.let { mensagem ->
            Text(
                text = mensagem,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.calcular() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA0EAB3)
            )
        ) {
            Text("Calcular IMC", color = Color.Black)
        }
    }
}