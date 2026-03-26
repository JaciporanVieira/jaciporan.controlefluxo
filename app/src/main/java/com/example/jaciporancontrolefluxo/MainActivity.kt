package com.example.jaciporancontrolefluxo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

/* JUSTIFICATIVA DA ESCOLHA (OPÇÃO 2):
   Escolhi a Opção 2 (Sealed Class) pois ela elimina a possibilidade de estados inválidos,
   como um usuário estar "logado" e "não logado" ao mesmo tempo. Com Sealed Class,
   o estado é único e exclusivo, permitindo que o compilador verifique se todos
   os fluxos foram tratados, tornando a navegação baseada em estado muito mais robusta.
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ControleFluxoApp()
            }
        }
    }
}

sealed class UserState {
    data object Loading : UserState()
    data object NotLogged : UserState()
    data object User : UserState()
    data object Admin : UserState()
}

@Composable
fun ControleFluxoApp() {
    var currentState by remember { mutableStateOf<UserState>(UserState.Loading) }

    LaunchedEffect(Unit) {
        delay(3000)
        currentState = UserState.NotLogged
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (currentState) {
            is UserState.Loading -> LoadingScreen()
            is UserState.NotLogged -> LoginScreen(
                onLoginUser = { currentState = UserState.User },
                onLoginAdmin = { currentState = UserState.Admin }
            )
            is UserState.User -> UserNavigationFlow(onLogout = { currentState = UserState.NotLogged })
            is UserState.Admin -> AdminDashboard(onLogout = { currentState = UserState.NotLogged })
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoginScreen(onLoginUser: () -> Unit, onLoginAdmin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Controle de Fluxo", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onLoginUser, modifier = Modifier.fillMaxWidth(0.7f)) {
            Text("Entrar como Usuário")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onLoginAdmin, modifier = Modifier.fillMaxWidth(0.7f)) {
            Text("Entrar como Admin")
        }
    }
}

@Composable
fun AdminDashboard(onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onLogout) { Text("Logout") }
    }
}

@Composable
fun UserNavigationFlow(onLogout: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, onLogout) }
        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavController, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home (Usuário)", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("profile") }) { Text("Ir para Perfil") }
        Button(onClick = { navController.navigate("settings") }) { Text("Ir para Configurações") }
        Spacer(modifier = Modifier.height(40.dp))
        TextButton(onClick = onLogout) { Text("Sair", color = MaterialTheme.colorScheme.error) }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        Text("Perfil")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) { Text("Voltar") }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        Text("Configurações")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) { Text("Voltar") }
    }
}