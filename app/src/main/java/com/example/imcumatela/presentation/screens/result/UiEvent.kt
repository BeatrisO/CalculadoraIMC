package com.example.imcumatela.presentation.screens.result

sealed interface ImcUiEvent {
    data class NavigateToResultado(
        val imc: Double,
        val categoria: String
    ) : ImcUiEvent
}
