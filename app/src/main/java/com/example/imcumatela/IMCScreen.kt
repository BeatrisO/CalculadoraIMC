package com.example.imcumatela

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun IMCScreen(onResultado: (Double, String) -> Unit) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }

    var pesoErro by remember { mutableStateOf(false) }
    var alturaErro by remember { mutableStateOf(false) }

    fun calcular() {
        pesoErro = peso.isBlank()
        alturaErro = altura.isBlank()

        if (pesoErro || alturaErro) return

        val p = peso.replace(",", ".").toDouble()

        val a = altura.replace(",", ".").toDouble()

        val imc = p / (a * a)

        val categoria = when {
            imc < 18.5 -> "Baixo peso"
            imc < 25.0 -> "Normal"
            imc < 30.0 -> "Sobrepeso"
            else -> "Obesidade"
        }

        onResultado(imc, categoria)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = pesoErro,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura (m)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = alturaErro,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { calcular() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Calcular IMC")
        }
    }
}