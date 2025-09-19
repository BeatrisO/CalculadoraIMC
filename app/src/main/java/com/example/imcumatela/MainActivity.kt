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

                val intent = Intent(this, ResultadoActivity::class.java)
                intent.putExtra("IMC", imc)
                intent.putExtra("CATEGORIA", categoria)
                startActivity(intent)
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
        return when {
            imc < 18.5 -> "Baixo peso"
            imc < 25.0 -> "Normal"
            imc < 30.0 -> "Sobrepeso"
            else -> "Obesidade"
        }
    }
}


