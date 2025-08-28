package com.example.imcumatela

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
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnCalcular.setOnClickListener {
            if (validacaoPeso()) {
                binding.editTextPeso.error = "Digite o peso"
            } else if (validacaoAltura()) {
                binding.editTextAltura.error = "Digite a altura"
            } else {
                val peso = binding.editTextPeso.text.toString().toDouble()
                val altura = binding.editTextAltura.text.toString().toDouble()
                val imc = calcular(peso, altura)
                val categoria = classificarIMC(imc)
                binding.textViewResultado.text = categoria
            }
        }
    }

    fun validacaoPeso(): Boolean {
        return binding.editTextPeso.text.isNullOrEmpty()
    }

    fun validacaoAltura(): Boolean {
        return binding.editTextAltura.text.isNullOrEmpty()
    }

    fun calcular(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }

    fun classificarIMC(imc: Double): String {
        if (imc < 18.5) {
            return "Abaixo do peso"
        } else if (imc < 25.0) {
            return "Peso normal"
        } else if (imc < 30.0) {
            return "Sobrepeso"
        } else if (imc < 35.0) {
            return "Obesidade grau I"
        } else if (imc < 40.0) {
            return "Obesidade grau II"
        } else {
            return "Obesidade grau III"
        }
    }
}

