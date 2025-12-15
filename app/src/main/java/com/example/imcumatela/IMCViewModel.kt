package com.example.imcumatela

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IMCViewModel : ViewModel() {

    private val _peso = MutableStateFlow("")
    val peso = _peso.asStateFlow()

    private val _altura = MutableStateFlow("")
    val altura = _altura.asStateFlow()

    private val _mensagem = MutableStateFlow("")
    val mensagem = _mensagem.asStateFlow()

    private val _pesoErro = MutableStateFlow(false)
    val pesoErro = _pesoErro.asStateFlow()

    private val _alturaErro = MutableStateFlow(false)
    val alturaErro = _alturaErro.asStateFlow()


    fun atualizarPeso(valor: String) {
        _peso.value = valor
        limparErros()
    }

    fun atualizarAltura(valor: String) {
        _altura.value = valor
        limparErros()
    }

    private fun limparErros() {
        _pesoErro.value = false
        _alturaErro.value = false
        _mensagem.value = ""
    }


    fun calcular(onResultado: (Double, String) -> Unit) {

        val pesoStr = _peso.value
        val alturaStr = _altura.value

        _pesoErro.value = pesoStr.isBlank()
        _alturaErro.value = alturaStr.isBlank()

        if (_pesoErro.value || _alturaErro.value) {
            _mensagem.value = when {
                _pesoErro.value -> "Digite o peso"
                _alturaErro.value -> "Digite a altura"
                else -> ""
            }
            return
        }

        val peso = pesoStr.replace(",", ".").toDoubleOrNull()
        val alturaCm = alturaStr.replace(",", ".").toDoubleOrNull()

        if (peso == null) {
            _pesoErro.value = true
            _mensagem.value = "Peso inválido"
            return
        }

        if (alturaCm == null) {
            _alturaErro.value = true
            _mensagem.value = "Altura inválida"
            return
        }

        val alturaM = alturaCm / 100.0
        val imc = peso / (alturaM * alturaM)

        val categoria = when {
            imc < 18.5 -> "Baixo peso"
            imc < 25.0 -> "Normal"
            imc < 30.0 -> "Sobrepeso"
            else -> "Obesidade"
        }

        onResultado(imc, categoria)
    }
}
