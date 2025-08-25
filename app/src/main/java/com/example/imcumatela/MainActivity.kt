package com.example.imcumatela

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextPeso: EditText
    private lateinit var editTextAltura: EditText
    private lateinit var btnClicar: Button
    private lateinit var textViewResultado: TextView

    fun configurarComponentes() {
        editTextPeso = findViewById(R.id.editText_Peso)
        editTextAltura = findViewById(R.id.editText_Altura)
        btnClicar = findViewById(R.id.btn_calcular)
        textViewResultado = findViewById(R.id.textView_resultado)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarComponentes()
        btnClicar.setOnClickListener {
            if (validacaoPeso()) {
                editTextPeso.setError("Digite o peso")
            } else if (validacaoAltura()) {
                editTextAltura.setError("Digite a altura")
            } else {
                val peso = editTextPeso.text.toString().toDouble()
                val altura = editTextAltura.text.toString().toDouble()
                val imc = calcular(peso, altura)
                val categoria = classificarIMC(imc)
                textViewResultado.text = categoria
            }

        }

    }

    fun validacaoPeso(): Boolean {
        return editTextPeso.text.isEmpty()
    }

    fun validacaoAltura(): Boolean {
        return editTextAltura.text.isEmpty()
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

