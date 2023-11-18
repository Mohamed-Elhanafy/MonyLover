package com.example.monylover.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.monylover.models.Category
import com.example.monylover.models.Expense
import com.example.monylover.ui.components.PickerTrigger
import com.example.monylover.models.Recurrence
import com.example.monylover.ui.components.expenses.ExpensesList
import com.example.monylover.ui.theme.LabelSecondary
import com.example.monylover.ui.theme.MonyLoverTheme
import com.example.monylover.ui.theme.TopAppBarBackground
import com.example.monylover.ui.theme.Typography
import com.example.monylover.viewmodels.ExpansesViewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

@ExperimentalMaterial3Api
@Composable
fun ExpanseScreen(
    navController: NavController,
    expanseViewModel: ExpansesViewModel = ExpansesViewModel()
) {

    val state by expanseViewModel.uiState.collectAsState()
    var recurrenceMenuExpanded by remember { mutableStateOf(false) }

    val list = listOf(
        Expense(
            amount = 100.0,
            note = "pizza",
            date = LocalDateTime.now().minus(
                Random.nextInt(300, 345600).toLong(),
                ChronoUnit.SECONDS
            ),
            recurrence = Recurrence.None,
            category = Category(
                name = "Food",
                Color(
                    Random.nextInt(0, 255),
                    Random.nextInt(0, 255),
                    Random.nextInt(0, 255)
                )
            )
        ),
        Expense(
            amount = 100.0,
            note = "Coctail",
            date = LocalDateTime.now(),
            recurrence = Recurrence.None,
            category = Category(
                name = "Bills",
                Color(
                    Random.nextInt(0, 255),
                    Random.nextInt(0, 255),
                    Random.nextInt(0, 255)
                )
            )
        ),
        Expense(
            amount = 104.0,
            note = "car wash",
            date = LocalDateTime.now(),
            recurrence = Recurrence.None,
            category = Category(
                name = "car",
                Color(
                    Random.nextInt(0, 255),
                    Random.nextInt(0, 255),
                    Random.nextInt(0, 255)
                )
            )
        )

    )
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Expanse") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackground
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Total for :", style = Typography.bodyMedium)

                    val recurrence = listOf(
                        Recurrence.None,
                        Recurrence.Daily,
                        Recurrence.Weekly,
                        Recurrence.Monthly,
                        Recurrence.Yearly
                    )
                    Box {
                        PickerTrigger(
                            label = state.recurrence.target,
                            onClick = { recurrenceMenuExpanded = true },
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        DropdownMenu(
                            expanded = recurrenceMenuExpanded,
                            onDismissRequest = { recurrenceMenuExpanded = false }) {
                            recurrence.forEach { label ->
                                DropdownMenuItem(
                                    text = { Text(text = label.target) },
                                    onClick = {
                                        expanseViewModel.setRecurrence(label)
                                        recurrenceMenuExpanded = false
                                    })
                            }
                        }
                    }


                }

                Row(modifier = Modifier.padding(vertical = 32.dp)) {
                    Text(
                        "$",
                        style = Typography.bodyMedium,
                        color = LabelSecondary,
                        modifier = Modifier.padding(end = 4.dp, top = 4.dp)
                    )
                    Text(
                        DecimalFormat("0.#").format(state.sumTotal),
                        style = Typography.titleLarge
                    )
                }

                ExpensesList(
                    list, modifier = Modifier
                        .weight(1f)
                        .verticalScroll(
                            rememberScrollState()
                        )
                )


            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ExpanseScreenPreview() {
    MonyLoverTheme {
        ExpanseScreen(navController = rememberNavController())
    }
}