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

// Головні розділи застосунку
enum class AppDestinations(val title: String) {
    HOME("Головна"),
    PROFILE("Профіль")
}

// Внутрішні підекрани для Головної сторінки (згідно з п. 1)
enum class HomeSubDestinations {
    MAIN_SUB,   // Підекран із розмітки попередньої лаби
    CUSTOM_SUB  // Другий довільний підекран
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

// Головний екран, що містить всередині себе мінімум два підекрани (п. 1 та 2)
@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    // Стан для перемикання між підекранами всередині Home
    var currentSubScreen by rememberSaveable { mutableStateOf(HomeSubDestinations.MAIN_SUB) }

    when (currentSubScreen) {
        HomeSubDestinations.MAIN_SUB -> {
            // Перший підекран: містить елементи з попередньої лабораторної
            FirstSubScreen(
                onOpenSecondSub = { currentSubScreen = HomeSubDestinations.CUSTOM_SUB },
                onNavigateToProfile = onNavigateToProfile
            )
        }
        HomeSubDestinations.CUSTOM_SUB -> {
            // Другий підекран: довільна розмітка (п. 1) + кнопка повернення назад (п. 3)
            SecondSubScreen(
                onBackToMain = { currentSubScreen = HomeSubDestinations.MAIN_SUB }
            )
        }
    }
}

// Перший підекран (розмітка з попередньої лабораторної + кнопка переходу)
@Composable
fun FirstSubScreen(
    onOpenSecondSub: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var clickCount by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Екран: Головна (Підекран 1)", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Кількість натискань (з минулої лаби): $clickCount", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { clickCount++ }) {
            Text("Змінити мітку (Клік!)")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка переходу на другий підекран всередині екрану (п. 1)
        Button(onClick = onOpenSecondSub) {
            Text("Перейти на другий підекран ➔")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateToProfile) {
            Text("Перейти до Профілю")
        }
    }
}

// Другий підекран із довільною розміткою (п. 1)
@Composable
fun SecondSubScreen(onBackToMain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Це другий довільний підекран!", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Тут може бути будь-який інший унікальний контент, списки чи налаштування.", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка повернення назад (п. 3)
        Button(onClick = onBackToMain) {
            Text("← Назад на перший підекран")
        }
    }
}

@Composable
fun ProfileScreen(onNavigateBack: () -> Unit) {
    var statusText by rememberSaveable { mutableStateOf("Статус: Активний користувач") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
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