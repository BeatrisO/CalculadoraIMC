package com.example.imcumatela

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun IMCScreen(onResultado: (Double, String) -> Unit) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }

    var pesoErro by remember { mutableStateOf(false) }
    var alturaErro by remember { mutableStateOf(false) }
    var mensagem by remember { mutableStateOf("") }

    fun calcular() {
        pesoErro = peso.isBlank()
        alturaErro = altura.isBlank()
        mensagem = ""

        if (pesoErro || alturaErro) {
            mensagem = if (pesoErro) "Digite o peso" else "Digite a altura"
            return
        }

        val pesoStr = peso.replace(",", ".")
        val alturaStr = altura.replace(",", ".")

        val p = pesoStr.toDoubleOrNull()
        val aCm = alturaStr.toDoubleOrNull()

        if (p == null) {
            pesoErro = true
            mensagem = "Peso inválido"
            return
        }
        if (aCm == null) {
            alturaErro = true
            mensagem = "Altura inválida"
            return
        }

        val a = aCm / 100.0

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
            onValueChange = {
                peso = it
                pesoErro = false
                mensagem = ""
            },
            label = { Text("Peso (kg)") },
            placeholder = { Text("ex: 65") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = pesoErro,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = altura,
            onValueChange = {
                altura = it
                alturaErro = false
                mensagem = ""
            },
            label = { Text("Altura (cm)") },
            placeholder = { Text("ex: 170") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = alturaErro,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (mensagem.isNotEmpty()) {
            Text(
                text = mensagem,
                color = if (alturaErro || pesoErro) Color.Red else Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { calcular() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Calcular IMC")
        }
    }
}