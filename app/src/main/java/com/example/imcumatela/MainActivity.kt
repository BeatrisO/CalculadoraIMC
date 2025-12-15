package com.example.imcumatela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import com.example.imcumatela.presentation.IMCScreen
import com.example.imcumatela.presentation.ResultadoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "imc"
                ) {

                    composable("imc") {
                        IMCScreen(
                            onResultado = { imc, categoria ->
                                navController.navigate("resultado/$imc/$categoria")
                            }
                        )
                    }

                    composable("resultado/{imc}/{categoria}") { backStackEntry ->
                        val imc = backStackEntry.arguments?.getString("imc")?.replace(",", ".")?.toDouble() ?: 0.0
                        val categoria = backStackEntry.arguments?.getString("categoria") ?: ""

                        ResultadoScreen(
                            imc = imc,
                            categoria = categoria,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

