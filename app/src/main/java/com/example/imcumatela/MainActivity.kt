package com.example.imcumatela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imcumatela.presentation.screens.calculation.IMCScreen
import com.example.imcumatela.presentation.screens.result.ResultadoScreen
import com.example.imcumatela.presentation.screens.calculation.IMCViewModel
import com.example.imcumatela.presentation.screens.history.HistoryScreen
import com.seuprojeto.ui.theme.ImcTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ImcTheme {
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
                            },
                            onNavigateHistory = {
                                navController.navigate("history")
                            }
                        )
                    }

                    composable("resultado") {
                        ResultadoScreen(
                            viewModel = imcViewModel,
                            onBack = {navController.popBackStack()
                            }
                        )
                    }
                    composable("history") {
                        HistoryScreen()
                    }
                }
            }
        }
    }
}
