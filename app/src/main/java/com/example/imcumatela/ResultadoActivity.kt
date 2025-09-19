package com.example.imcumatela

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.imcumatela.databinding.ActivityResultadoBinding

class ResultadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imc = intent.getDoubleExtra("IMC", 0.0)
        val categoria = intent.getStringExtra("CATEGORIA")
        val imcFormatado = "%.1f".format(imc)

        binding.textViewResultado.text = "IMC: $imcFormatado\nClassificação: $categoria"

        val cor = when (categoria) {
            "Baixo peso" -> ContextCompat.getColor(this, R.color.baixo_peso)
            "Normal" -> ContextCompat.getColor(this, R.color.normal)
            "Sobrepeso" -> ContextCompat.getColor(this, R.color.sobrepeso)
            else -> ContextCompat.getColor(this, R.color.obesidade)
        }
        binding.caixaResultado.setCardBackgroundColor(cor)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}