package com.example.bmijetpackcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.bmijetpackcomposer.ui.theme.BMIJetpackComposerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el disseny d'extrem a extrem (Edge-to-Edge) ocupant tota la pantalla incloent les barres del sistema
        enableEdgeToEdge()
        // Defineix la interfície d'usuari mitjançant Jetpack Compose
        setContent {
            BMIJetpackComposerTheme {
                BMIJetpackComposerApp()
            }
        }
    }
}


@PreviewScreenSizes
@Composable
fun BMIJetpackComposerApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it } // Canvia la pestanya seleccionada
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            BMIScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}


enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}


@Preview
@Composable
fun BMIScreen(modifier: Modifier = Modifier) {
    // Estat per guardar el nom introduït per l'usuari
    var nom: String by remember { mutableStateOf("") }
    // Estat enter per al pes en Kg (s'inicia a 80)
    var pes: Int by remember { mutableIntStateOf(80) }

    var alcada: Int by remember { mutableIntStateOf(170) }

    var bmi: Float by remember { mutableStateOf(0f) }

    Column(modifier = modifier) {
        Text(text = "IMC Calculadora")

        TextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom") }
        )


        Text(text = "Pes")

        Row {
            // Mostra el pes en Kg
            Text(text = "$pes Kg")

            // Botó per incrementar el pes
            Button(onClick = { pes++ }) {
                Text(text = "+")
            }

            // Botó per disminuir el pes
            Button(onClick = { pes-- }) {
                Text(text = "-")
            }
        }
        Text(text = "Alçada")

        Text(text = "Alçada: $alcada cm")
        Slider(
            value = alcada.toFloat(),
            onValueChange = { alcada = it.toInt() },
            valueRange = 100f..250f
        )

        Button(
            onClick = {
                var alcadaMetres: Float = alcada.toFloat() / 100
                bmi = (pes / (alcadaMetres * alcadaMetres))
            }
        ) {
            Text(text = "Calcular IMC")
        }
        if(bmi != 0f){
            Text(text = "IMC: $bmi")
        }
    }
}