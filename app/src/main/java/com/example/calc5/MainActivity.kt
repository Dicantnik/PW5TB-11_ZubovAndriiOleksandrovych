package com.example.calc5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calc5.ui.theme.Calc5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calc5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TabNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TabNavigation(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) } // Вибір активної вкладки

    // Масив назв вкладок
    val tabs = listOf("Page 1", "Page 2")

    Column(modifier = modifier) {
        // Створення рядка вкладок
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        // Контент залежно від активної вкладки
        when (selectedTabIndex) {
            0 -> FirstCalc() // Контент для першої сторінки
            1 -> SecondCalc() // Контент для другої сторінки
        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Calc5Theme {
        TabNavigation()
    }
}
