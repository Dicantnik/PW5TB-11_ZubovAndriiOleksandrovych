package com.example.calc5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calc5.ui.theme.Calc5Theme

@Composable
fun SecondCalc() {
    // Окремі змінні для кожного поля
    var input1 by remember { mutableStateOf("0.01") }
    var input2 by remember { mutableStateOf("0.045") }
    var input3 by remember { mutableStateOf("5120") }
    var input4 by remember { mutableStateOf("6451") }
    var input5 by remember { mutableStateOf("23.6") }
    var input6 by remember { mutableStateOf("17.6") }
    var input7 by remember { mutableStateOf("0.004") }

    var result by remember { mutableStateOf("") } // Результат обчислення

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp), // Відстань між елементами
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поля для вводу
        TextField(
            value = input1,
            onValueChange = { input1 = it },
            label = { Text("Частота відмов") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = input2,
            onValueChange = { input2 = it },
            label = { Text("Сер час відновл 35вт") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = input3,
            onValueChange = { input3 = it },
            label = { Text("Потужність") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = input4,
            onValueChange = { input4 = it },
            label = { Text("Час простою очік") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = input5,
            onValueChange = { input5 = it },
            label = { Text("Збитки у разі аварійного перивання") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = input6,
            onValueChange = { input6 = it },
            label = { Text("Збитики у разі запланованого") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = input7,
            onValueChange = { input7 = it },
            label = { Text("Середній час план прост") },
            modifier = Modifier.fillMaxWidth()
        )

        // Кнопка для обчислення
        Button(
            onClick = {

                val input1_val = input1.toDoubleOrNull() ?: 0.0
                val input2_val = input2.toDoubleOrNull() ?: 0.0
                val input3_val = input3.toDoubleOrNull() ?: 0.0
                val input4_val = input4.toDoubleOrNull() ?: 0.0
                val input5_val = input5.toDoubleOrNull() ?: 0.0
                val input6_val = input6.toDoubleOrNull() ?: 0.0
                val input7_val = input7.toDoubleOrNull() ?: 0.0

                val result1 = input1_val * input2_val * input3_val * input4_val
                val result2 = input7_val * input3_val * input4_val
                val result3 = input5_val * result1 + input6_val * result2

//                result_display1 = result1.toString()
//                result_display2 = result2.toString()
//                result_display3 = result3.toString()


                result = """
Очік відсутність енергопостачання в надзв сит - ${"%.2f".format(result1)}
Очік дефіцит енергії в запланован сит - ${"%.2f".format(result2)}
Загальна очік вартівсть перерв - ${"%.2f".format(result3)}
                """
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Обчилити")
        }

        // Виведення результату
        if (result.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.TopStart // Прижимає текст до початку
            ) {
                Text(
                    text = result,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondCalcPreview() {
    Calc5Theme {
        SecondCalc()
    }
}
