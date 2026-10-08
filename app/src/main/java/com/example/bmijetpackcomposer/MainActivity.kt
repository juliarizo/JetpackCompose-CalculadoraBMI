package com.example.bmijetpackcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmijetpackcomposer.ui.theme.BMIJetpackComposerTheme
import com.example.bmijetpackcomposer.ui.theme.DarkBackground
import com.example.bmijetpackcomposer.ui.theme.DarkCardBorder
import com.example.bmijetpackcomposer.ui.theme.DarkSurface
import com.example.bmijetpackcomposer.ui.theme.DarkSurfaceVariant
import com.example.bmijetpackcomposer.ui.theme.ElectricCyan
import com.example.bmijetpackcomposer.ui.theme.ElectricViolet
import com.example.bmijetpackcomposer.ui.theme.FuchsiaGlow
import com.example.bmijetpackcomposer.ui.theme.FuchsiaLight
import com.example.bmijetpackcomposer.ui.theme.FuchsiaPrimary
import com.example.bmijetpackcomposer.ui.theme.NeonGreen
import com.example.bmijetpackcomposer.ui.theme.NeonOrange
import com.example.bmijetpackcomposer.ui.theme.NeonYellow
import com.example.bmijetpackcomposer.ui.theme.TextPrimary
import com.example.bmijetpackcomposer.ui.theme.TextSecondary

/**
 * Modifier personalizado para crear halos/sombras de neón fucsia alrededor de los componentes.
 */
fun Modifier.fuchsiaGlow(
    color: Color = FuchsiaGlow,
    cornerRadius: Dp = 20.dp,
    glowRadius: Dp = 10.dp,
    alpha: Float = 0.30f
): Modifier = this
    .shadow(
        elevation = 6.dp,
        shape = RoundedCornerShape(cornerRadius),
        spotColor = color,
        ambientColor = color
    )
    .drawBehind {
        drawRoundRect(
            color = color.copy(alpha = alpha),
            size = Size(size.width + glowRadius.toPx(), size.height + glowRadius.toPx()),
            topLeft = Offset(-glowRadius.toPx() / 2f, -glowRadius.toPx() / 2f),
            cornerRadius = CornerRadius((cornerRadius + glowRadius / 2f).toPx())
        )
    }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

    val navSuiteColors = NavigationSuiteDefaults.colors(
        navigationBarContainerColor = DarkSurface,
        navigationBarContentColor = TextSecondary,
        navigationRailContainerColor = DarkSurface,
        navigationRailContentColor = TextSecondary
    )

    NavigationSuiteScaffold(
        navigationSuiteColors = navSuiteColors,
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                val isSelected = it == currentDestination
                item(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.label,
                            tint = if (isSelected) FuchsiaPrimary else TextSecondary
                        )
                    },
                    label = {
                        Text(
                            text = it.label,
                            color = if (isSelected) FuchsiaPrimary else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    selected = isSelected,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = DarkBackground
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(DarkBackground)
            ) {
                when (currentDestination) {
                    AppDestinations.HOME -> BMIScreen()
                    AppDestinations.FAVORITES -> FavoritesPlaceholderScreen()
                    AppDestinations.PROFILE -> ProfilePlaceholderScreen()
                }
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Inicio", Icons.Default.Home),
    FAVORITES("Favoritos", Icons.Default.Favorite),
    PROFILE("Perfil", Icons.Default.AccountBox),
}

@Preview
@Composable
fun BMIScreen(modifier: Modifier = Modifier) {
    var nom: String by remember { mutableStateOf("") }
    var pes: Int by remember { mutableIntStateOf(75) }
    var alcada: Int by remember { mutableIntStateOf(175) }

    val heightInMeters = alcada / 100f
    val bmiValue = if (heightInMeters > 0) pes / (heightInMeters * heightInMeters) else 0f
    val bmiCategory = getBmiCategory(bmiValue)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection()

        NameInputCard(
            nom = nom,
            onNomChange = { nom = it }
        )

        WeightInputCard(
            pes = pes,
            onPesChange = { pes = it }
        )

        HeightInputCard(
            alcada = alcada,
            onAlcadaChange = { alcada = it }
        )

        BmiResultCard(
            nom = nom,
            bmiValue = bmiValue,
            category = bmiCategory
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .fuchsiaGlow(color = FuchsiaPrimary, cornerRadius = 24.dp, glowRadius = 8.dp)
                .background(
                    brush = Brush.linearGradient(listOf(FuchsiaPrimary, ElectricViolet)),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.FitnessCenter,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "CALCULADORA IMC",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                letterSpacing = 1.sp
            )
            Text(
                text = "Índice de Masa Corporal",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = FuchsiaLight
            )
        }
    }
}

@Composable
fun NameInputCard(
    nom: String,
    onNomChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fuchsiaGlow(color = DarkCardBorder, cornerRadius = 16.dp, glowRadius = 6.dp, alpha = 0.2f),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = ElectricCyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Nombre del usuario",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nom,
                onValueChange = onNomChange,
                placeholder = { Text("Introduce tu nombre", color = TextSecondary.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FuchsiaPrimary,
                    unfocusedBorderColor = DarkCardBorder,
                    focusedContainerColor = DarkSurfaceVariant,
                    unfocusedContainerColor = DarkSurfaceVariant,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
        }
    }
}

@Composable
fun WeightInputCard(
    pes: Int,
    onPesChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fuchsiaGlow(color = FuchsiaGlow, cornerRadius = 16.dp, glowRadius = 8.dp, alpha = 0.25f),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, FuchsiaPrimary.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.MonitorWeight,
                        contentDescription = null,
                        tint = FuchsiaLight,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PESO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(-5, +5).forEach { delta ->
                        Surface(
                            onClick = { onPesChange((pes + delta).coerceIn(20, 300)) },
                            shape = RoundedCornerShape(8.dp),
                            color = DarkSurfaceVariant,
                            border = BorderStroke(1.dp, DarkCardBorder)
                        ) {
                            Text(
                                text = if (delta > 0) "+$delta" else "$delta",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = FuchsiaLight,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$pes",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "kg",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = FuchsiaPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (pes > 20) onPesChange(pes - 1) },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(DarkSurfaceVariant, DarkCardBorder)),
                            shape = CircleShape
                        )
                        .border(1.dp, FuchsiaPrimary.copy(alpha = 0.5f), CircleShape),
                    colors = IconButtonDefaults.iconButtonColors(contentColor = TextPrimary)
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Disminuir peso")
                }

                Spacer(modifier = Modifier.width(32.dp))

                IconButton(
                    onClick = { if (pes < 300) onPesChange(pes + 1) },
                    modifier = Modifier
                        .size(48.dp)
                        .fuchsiaGlow(color = FuchsiaPrimary, cornerRadius = 24.dp, glowRadius = 6.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(FuchsiaPrimary, ElectricViolet)),
                            shape = CircleShape
                        ),
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Aumentar peso")
                }
            }
        }
    }
}

@Composable
fun HeightInputCard(
    alcada: Int,
    onAlcadaChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fuchsiaGlow(color = ElectricCyan, cornerRadius = 16.dp, glowRadius = 8.dp, alpha = 0.20f),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Height,
                    contentDescription = null,
                    tint = ElectricCyan,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ALTURA",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$alcada",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "cm",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElectricCyan,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = alcada.toFloat(),
                onValueChange = { onAlcadaChange(it.toInt()) },
                valueRange = 100f..230f,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = ElectricCyan,
                    activeTrackColor = ElectricCyan,
                    inactiveTrackColor = DarkSurfaceVariant
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "100 cm", fontSize = 11.sp, color = TextSecondary)
                Text(text = "230 cm", fontSize = 11.sp, color = TextSecondary)
            }
        }
    }
}

@Composable
fun BmiResultCard(
    nom: String,
    bmiValue: Float,
    category: BmiCategory
) {
    val animatedBmiValue by animateFloatAsState(targetValue = bmiValue, label = "bmi")
    val categoryColor by animateColorAsState(targetValue = category.color, label = "categoryColor")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fuchsiaGlow(color = categoryColor, cornerRadius = 20.dp, glowRadius = 12.dp, alpha = 0.35f),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.5.dp, categoryColor.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (nom.isNotBlank()) "RESULTADO DE $nom" else "TU RESULTADO",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .fuchsiaGlow(color = categoryColor, cornerRadius = 55.dp, glowRadius = 12.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                categoryColor.copy(alpha = 0.3f),
                                DarkSurfaceVariant
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(2.dp, categoryColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "%.1f".format(animatedBmiValue),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary
                    )
                    Text(
                        text = "IMC",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = categoryColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = categoryColor.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, categoryColor)
            ) {
                Text(
                    text = category.title.uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = categoryColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = category.description,
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val progress = ((bmiValue - 15f) / (35f - 15f)).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = categoryColor,
                trackColor = DarkSurfaceVariant,
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun FavoritesPlaceholderScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fuchsiaGlow(color = FuchsiaPrimary, cornerRadius = 20.dp, glowRadius = 10.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, FuchsiaPrimary.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = FuchsiaPrimary,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Pestaña Favoritos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Guarda aquí tus registros de IMC preferidos.",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ProfilePlaceholderScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fuchsiaGlow(color = ElectricCyan, cornerRadius = 20.dp, glowRadius = 10.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = ElectricCyan,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Perfil de Usuario",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Configura tus metas físicas y ajustes personales.",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

enum class BmiCategory(
    val title: String,
    val description: String,
    val color: Color
) {
    UNDERWEIGHT(
        title = "Bajo peso",
        description = "Tu IMC indica que estás por debajo del peso recomendado.",
        color = NeonYellow
    ),
    NORMAL(
        title = "Peso normal",
        description = "¡Felicidades! Tu IMC está en un rango saludable e ideal.",
        color = NeonGreen
    ),
    OVERWEIGHT(
        title = "Sobrepeso",
        description = "Tu IMC indica un ligero sobrepeso. Se aconseja actividad física.",
        color = NeonOrange
    ),
    OBESE(
        title = "Obesidad",
        description = "Tu IMC está en rango de obesidad. Consulta con un especialista.",
        color = FuchsiaPrimary
    )
}

fun getBmiCategory(bmi: Float): BmiCategory {
    return when {
        bmi < 18.5f -> BmiCategory.UNDERWEIGHT
        bmi < 25.0f -> BmiCategory.NORMAL
        bmi < 30.0f -> BmiCategory.OVERWEIGHT
        else -> BmiCategory.OBESE
    }
}
