package com.nickolss.aula_tapweb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nickolss.aula_tapweb.ui.theme.AulatapwebTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AulatapwebTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                onLogin = { userName ->
                                    navController.navigate("home/${userName.ifBlank { "Convidado" }}")
                                },
                                onRegisterClick = {
                                    navController.navigate("register")
                                }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onRegisterComplete = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            "home/{userName}",
                            arguments = listOf(navArgument("userName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val userName =
                                backStackEntry.arguments?.getString("userName") ?: "Usuário"
                            HomeScreen(
                                userName = userName,
                                onCadastrarProduto = {
                                    navController.navigate("cadastrarProduto")
                                },
                                onListarProdutos = {
                                    navController.navigate("listarProdutos")
                                },
                                onLogout = {
                                    navController.popBackStack("login", inclusive = false)
                                }
                            )
                        }
                        composable("listarProdutos") {
                            ListarProdutosScreen(onBack = { navController.popBackStack() })
                        }
                        composable("cadastrarProduto") {
                            CadastrarProdutoScreen(
                                onRegisterComplete = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
