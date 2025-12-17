package com.example.imcumatela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imcumatela.presentation.imc.IMCScreen
import com.example.imcumatela.presentation.result.ResultadoScreen
import com.example.imcumatela.viewmodel.IMCViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val imcViewModel: IMCViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "imc"
                ) {
                    composable("imc") {
                        IMCScreen(
                            viewModel = imcViewModel,
                            onNavigateResultado = {
                                navController.navigate("resultado")
                            }
                        )
                    }

                    composable("resultado") {
                        ResultadoScreen(
                            viewModel = imcViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

