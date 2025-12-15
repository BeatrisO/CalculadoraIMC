package com.example.imcumatela.presentation

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
import com.example.imcumatela.IMCViewModel

@Composable
fun IMCScreen(
    viewModel: IMCViewModel = viewModel(),
    onResultado: (Double, String) -> Unit
) {

    val peso by viewModel.peso.collectAsState()
    val altura by viewModel.altura.collectAsState()
    val mensagem by viewModel.mensagem.collectAsState()
    val pesoErro by viewModel.pesoErro.collectAsState()
    val alturaErro by viewModel.alturaErro.collectAsState()

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
            value = peso,
            onValueChange = { viewModel.atualizarPeso(it) },
            label = { Text("Peso (kg)") },
            placeholder = { Text("ex: 65") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = pesoErro,
            colors = textFieldBlackColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = altura,
            onValueChange = { viewModel.atualizarAltura(it) },
            label = { Text("Altura (cm)") },
            placeholder = { Text("ex: 170") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = alturaErro,
            colors = textFieldBlackColors
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (mensagem.isNotEmpty()) {
            Text(
                text = mensagem,
                color = if (pesoErro || alturaErro) MaterialTheme.colorScheme.error else Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.calcular(onResultado) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA0EAB3)
            )
        ) {
            Text("Calcular IMC", color = Color.Black)
        }
    }
}