package com.example.imcumatela

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imcumatela.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupListeners()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        binding.btnCalcular.setOnClickListener {
            if (!validarCampos()) return@setOnClickListener
            calcularIMC()
        }
    }

    private fun validarCampos(): Boolean {
        var valido = true

        if (binding.editTextPeso.text.isNullOrBlank()) {
            binding.editTextPeso.error = "Digite o peso"
            valido = false
        }

        if (binding.editTextAltura.text.isNullOrBlank()) {
            binding.editTextAltura.error = "Digite a altura"
            valido = false
        }

        return valido
    }

    private fun calcularIMC() {
        val peso = binding.editTextPeso.text.toString().toDouble()
        val altura = binding.editTextAltura.text.toString().toDouble()

        val imc = peso / (altura * altura)
        val categoria = classificarIMC(imc)

        val intent = Intent(this, ResultadoActivity::class.java).apply {
            putExtra("IMC", imc)
            putExtra("CATEGORIA", categoria)
        }

        startActivity(intent)
    }

    private fun classificarIMC(imc: Double): String =
        when {
            imc < 18.5 -> "Baixo peso"
            imc < 25.0 -> "Normal"
            imc < 30.0 -> "Sobrepeso"
            else -> "Obesidade"
        }
}

