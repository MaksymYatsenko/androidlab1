package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1.ui.theme.Lab1Theme

// 5. Елементи навігації (enum class)
enum class AppDestinations(val title: String) {
    HOME("Головна"),
    PROFILE("Профіль")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Простий перемикач екранів для демонстрації
                    var currentScreen by remember { mutableStateOf(AppDestinations.HOME) }

                    when (currentScreen) {
                        AppDestinations.HOME -> HomeScreen(
                            onNavigateToProfile = { currentScreen = AppDestinations.PROFILE }
                        )
                        AppDestinations.PROFILE -> ProfileScreen(
                            onNavigateBack = { currentScreen = AppDestinations.HOME }
                        )
                    }
                }
            }
        }
    }
}

// 6 & 7. Перший екран з вирівнюванням по лівому краю та rememberSaveable
@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    var clickCount by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start // Вирівнювання ліворуч
    ) {
        Text(text = "Екран: Головна", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Кількість натискань: $clickCount", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { clickCount++ }) {
            Text("Змінити мітку (Клік!)")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onNavigateToProfile) {
            Text("Перейти до Профілю ➔")
        }
    }
}

// 6. Другий екран із вирівнюванням по центру
@Composable
fun ProfileScreen(onNavigateBack: () -> Unit) {
    var statusText by rememberSaveable { mutableStateOf("Статус: Активний користувач") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally // Вирівнювання по центру
    ) {
        Text(text = "Мій профіль", fontSize = 28.sp)
        Text(text = statusText, fontSize = 18.sp)

        Button(onClick = { statusText = "Статус: Успішно оновлено!" }) {
            Text("Оновити статус")
        }

        Button(onClick = onNavigateBack) {
            Text("← Назад на Головну")
        }
    }
}