package com.example.imcumatela.presentation.imc

data class ImcUiState(
    val peso: String = "",
    val altura: String = "",
    val imc: Double? = null,
    val categoria: String = "",
    val mensagemErro: String? = null,
    val pesoErro: Boolean = false,
    val alturaErro: Boolean = false
)