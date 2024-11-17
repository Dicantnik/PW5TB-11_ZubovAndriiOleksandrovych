package com.example.calc5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calc5.ui.theme.Calc5Theme


data class tablevalue(
    val firstval: Double,
    val secondtval: Int,
    val thirdval: Double,
    val fourthval: Int,
)

val datatable = mapOf(
    "ПЛ-110 кВ" to tablevalue(0.007, 10, 0.167, 35),
    "ПЛ-35 кВ" to tablevalue(0.02, 8, 0.167, 35),
    "ПЛ-10 кВ" to tablevalue(0.02, 10, 0.167, 35),
    "КЛ-10 кВ (траншея)" to tablevalue(0.03, 44, 1.0, 9),
    "КЛ-10 кВ (кабельний канал)" to tablevalue(0.005, 18, 1.0, 9),
    "T-110 kV" to tablevalue(0.015, 100, 1.0, 43),
    "T-35 kV" to tablevalue(0.02, 80, 1.0, 28),
    "T-10 kV (кабельна мережа 10 кВ)" to tablevalue(0.005, 60, 0.5, 10),
    "T-10 kV (повітряна мережа 10 кВ)" to tablevalue(0.05, 60, 0.5, 10),
    "B-110 kV (елегазовий)" to tablevalue(0.01, 30, 0.1, 30),
    "B-10 kV (малолойний)" to tablevalue(0.02, 15, 0.33, 15),
    "B-10 kV (вакуумний)" to tablevalue(0.05, 15, 0.33, 15),
    "Збірні шини 10 кВ на 1 приєднання" to tablevalue(0.03, 2, 0.33, 15),
    "АВ-0,38 кВ" to tablevalue(0.05, 20, 1.0, 15),
    "ЕД 6,10 кВ" to tablevalue(0.1, 50, 0.5, 0),
    "ЕД 0,38 кВ" to tablevalue(0.1, 50, 0.5, 0),
)


@Composable
fun FirstCalc() {
    var inputvalues = mapOf(
        "ПЛ-110 кВ" to remember { mutableStateOf("10") },
        "ПЛ-35 кВ" to remember { mutableStateOf("0") },
        "ПЛ-10 кВ" to remember { mutableStateOf("0") },
        "КЛ-10 кВ (траншея)" to remember { mutableStateOf("0") },
        "КЛ-10 кВ (кабельний канал)" to remember { mutableStateOf("0") },
        "T-110 kV" to remember { mutableStateOf("1") },
        "T-35 kV" to remember { mutableStateOf("0") },
        "T-10 kV (кабельна мережа 10 кВ)" to remember { mutableStateOf("0") },
        "T-10 kV (повітряна мережа 10 кВ)" to remember { mutableStateOf("0") },
        "B-110 kV (елегазовий)" to remember { mutableStateOf("1") },
        "B-10 kV (малолойний)" to remember { mutableStateOf("1") },
        "B-10 kV (вакуумний)" to remember { mutableStateOf("0") },
        "Збірні шини 10 кВ на 1 приєднання" to remember { mutableStateOf("6") },
        "АВ-0,38 кВ" to remember { mutableStateOf("0") },
        "ЕД 6,10 кВ" to remember { mutableStateOf("0") },
        "ЕД 0,38 кВ" to remember { mutableStateOf("0") },
    )
    var result by remember { mutableStateOf("") } // Результат обчислення

    // Додаємо прокручування
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        datatable.forEach { (key, _) ->
            TextField(
                value = inputvalues[key]?.value ?: "",
                onValueChange = { inputvalues[key]?.value = it },
                label = { Text(key) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                result = calculateMetrics(inputvalues)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Обчилити")
        }

        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

fun calculateMetrics(inputs: Map<String, MutableState<String>>): String {
    var overallFailureRate = 0.0
    var avgRestorationTime = 0.0
    var systemIdleCoef = 0.0
    var systemIdleCoef2 = 0.0
    var twoCircuitsFailure = 0.0
    var combinedMetric = 0.0

    inputs.forEach { (key, state) ->
        val amount = state.value.toIntOrNull() ?: 0
        val metric = datatable[key]
        if (amount > 0 && metric != null) {
            overallFailureRate += amount * metric.firstval
            avgRestorationTime += amount * metric.secondtval * metric.firstval
        }
    }

    if (overallFailureRate > 0) {
        avgRestorationTime /= overallFailureRate
    }
    systemIdleCoef = (avgRestorationTime * overallFailureRate) / 8760
    systemIdleCoef2 = 1.2 * 43 / 8760
    twoCircuitsFailure = 2 * overallFailureRate * (systemIdleCoef + systemIdleCoef2)
    combinedMetric = twoCircuitsFailure + 0.02

    return buildString {
        append("Частота відмов однокол сис: %.3f\n".format(overallFailureRate))
        append("Сер трив відновл: %.3f\n".format(avgRestorationTime))
        append("Коеф авар простою однокол сис: %.10f\n".format(systemIdleCoef))
        append("Коеф план простою однокол сис: %.5f\n".format(systemIdleCoef2))
        append("Частота відмови одночасно двох: %.5f\n".format(twoCircuitsFailure))
        append("Частота відмови двокол сис з секц вимикач: %.5f\n".format(combinedMetric))
    }
}
