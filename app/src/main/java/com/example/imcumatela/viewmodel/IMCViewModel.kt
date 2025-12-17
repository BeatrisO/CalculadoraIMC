package com.example.imcumatela.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imcumatela.presentation.result.ImcUiEvent
import com.example.imcumatela.presentation.imc.ImcUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IMCViewModel : ViewModel() {

    private val State = MutableStateFlow(ImcUiState())
    val uiState: StateFlow<ImcUiState> = State.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ImcUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun atualizarPeso(valor: String) {
        State.update {
            it.copy(
                peso = valor,
                pesoErro = false,
                mensagemErro = null
            )
        }
    }

    fun atualizarAltura(valor: String) {
        State.update {
            it.copy(
                altura = valor,
                alturaErro = false,
                mensagemErro = null
            )
        }
    }

    fun calcular() {
        val state = State.value

        val pesoStr = state.peso
        val alturaStr = state.altura

        val pesoErro = pesoStr.isBlank()
        val alturaErro = alturaStr.isBlank()

        if (pesoErro || alturaErro) {
            State.update {
                it.copy(
                    pesoErro = pesoErro,
                    alturaErro = alturaErro,
                    mensagemErro = when {
                        pesoErro -> "Digite o peso"
                        alturaErro -> "Digite a altura"
                        else -> null
                    }
                )
            }
            return
        }

        val peso = pesoStr.replace(",", ".").toDoubleOrNull()
        val alturaCm = alturaStr.replace(",", ".").toDoubleOrNull()

        if (peso == null) {
            State.update {
                it.copy(
                    pesoErro = true,
                    mensagemErro = "Peso inválido"
                )
            }
            return
        }

        if (alturaCm == null) {
            State.update {
                it.copy(
                    alturaErro = true,
                    mensagemErro = "Altura inválida"
                )
            }
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

        State.update {
            it.copy(
                imc = imc,
                categoria = categoria,
                mensagemErro = null
            )
        }

        viewModelScope.launch {
            _uiEvent.emit(
                ImcUiEvent.NavigateToResultado(
                    imc = imc,
                    categoria = categoria
                )
            )
        }
    }
}

