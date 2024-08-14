package com.moneymanagement.mymoney.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.ui.components.BottomNavigationBar
import io.jetchart.common.animation.fadeInAnimation
import io.jetchart.gauge.GaugeArcDrawer
import io.jetchart.gauge.GaugeChart
import io.jetchart.gauge.NeedleDrawer
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line


@Composable
fun StatisticsScreen(navController: NavHostController,statisticsScreenViewModel: StatisticsScreenViewModel,homeScreenViewmodel: HomeScreenViewmodel) {
    val totalBalance by homeScreenViewmodel.totalBalance.collectAsState()
    val thisMonthExpense by homeScreenViewmodel.thisMonthExpense.collectAsState()
    val currentMonthTransactions by homeScreenViewmodel.currentMonthTransactions.collectAsState()
    val currentMonthIncome by statisticsScreenViewModel.currentMonthIncome.collectAsState()
    val currentMonthExpense by statisticsScreenViewModel.currentMonthExpense.collectAsState()
    val expenseTypeAmount by statisticsScreenViewModel.expenseTypeAmount.collectAsState()
    var max by remember {
        mutableDoubleStateOf(5.0)
    }

    var min by remember {
        mutableDoubleStateOf(0.0)
    }

    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) { it ->
            println(currentMonthTransactions)
            statisticsScreenViewModel.fetchData(currentMonthTransactions)
            println(currentMonthIncome)
            println(currentMonthExpense)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
            , horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .fillMaxHeight(.3f)
            ) {
                Text(
                    text = "Budget Condition",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                GaugeChart(
                    percentValue = (((totalBalance - thisMonthExpense) * 100) /totalBalance).toFloat(), //between 0 and 100
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    animation = fadeInAnimation(4000),
                    pointerDrawer = NeedleDrawer(needleColor = MaterialTheme.colorScheme.primary, baseSize = 12.dp),
                    arcDrawer = GaugeArcDrawer(thickness = 32.dp, cap = StrokeCap.Round)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .fillMaxHeight(.6f)
            ) {
                Text(
                    text = "This Week",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                println(expenseTypeAmount)

                if(expenseTypeAmount.isNotEmpty()){
                    max= expenseTypeAmount.maxOrNull() ?: 5.0
                    min = expenseTypeAmount.minOrNull() ?: 0.0
                }
                println("$max  $min")
                ColumnChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, bottom = 50.dp),
                    data = listOf(
                        Bars(
                            label = "Health",
                            values = listOf(
                                Bars.Data(
                                    label = "Expense",
                                    value = if(expenseTypeAmount.isNotEmpty()) expenseTypeAmount[0] else 0.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                            )
                        ),
                        Bars(
                            label = "Food",
                            values = listOf(
                                Bars.Data(
                                    label = "Expense",
                                    value = if(expenseTypeAmount.isNotEmpty()) expenseTypeAmount[1] else 0.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                            )
                        ),
                        Bars(
                            label = "Education",
                            values = listOf(
                                Bars.Data(
                                    label = "Expense",
                                    value = if(expenseTypeAmount.isNotEmpty()) expenseTypeAmount[2] else 0.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                            )
                        ),
                        Bars(
                            label = "Transportation",
                            values = listOf(
                                Bars.Data(
                                    label = "Expense",
                                    value = if(expenseTypeAmount.isNotEmpty()) expenseTypeAmount[3] else 0.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                            )
                        ),
                        Bars(
                            label = "Accommodation",
                            values = listOf(
                                Bars.Data(
                                    label = "Expense",
                                    value = if(expenseTypeAmount.isNotEmpty()) expenseTypeAmount[4] else 0.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                            )
                        ),
                        Bars(
                            label = "Others",
                            values = listOf(
                                Bars.Data(
                                    label = "Expense",
                                    value = if(expenseTypeAmount.isNotEmpty()) expenseTypeAmount[5] else 0.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                            )
                        )
                    ),
                    barProperties = BarProperties(
                        cornerRadius = Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
                        spacing = 3.dp,
                        thickness = 20.dp
                    ),
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    maxValue = max,
                    minValue = min,
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "This Month",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                LineChart(
                    data = listOf(
                        Line(
                            label = "Expense",
                            values = if(currentMonthExpense.isNotEmpty()) currentMonthExpense else listOf(0.0,0.0,0.0,0.0),
                            color = SolidColor(Color.Red),
                            curvedEdges = true,
                            dotProperties = DotProperties(
                                enabled = true,
                                color = SolidColor(Color.White),
                                strokeWidth = 4.dp,
                                radius = 2.dp,
                                strokeColor = SolidColor(Color.Red),
                            )
                        ),
                        Line(
                            label = "Income",
                            values = if(currentMonthIncome.isNotEmpty()) currentMonthIncome else listOf(0.0,0.0,0.0,0.0),
                            color = SolidColor(Color.Green),
                            curvedEdges = false,
                            dotProperties = DotProperties(
                                    enabled = true,
                                color = SolidColor(Color.White),
                                strokeWidth = 4.dp,
                                radius = 2.dp,
                                strokeColor = SolidColor(Color.Green),
                            )
                        ),
                    )
                )
            }

        }
    }
}