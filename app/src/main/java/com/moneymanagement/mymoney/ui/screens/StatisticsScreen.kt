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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line


@Composable
fun StatisticsScreen(navController: NavHostController) {

    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
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
                    percentValue = 72f, //between 0 and 100
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    animation = fadeInAnimation(4000),
                    pointerDrawer = NeedleDrawer(needleColor = MaterialTheme.colorScheme.primary, baseSize = 12.dp),
                    arcDrawer = GaugeArcDrawer(thickness = 32.dp, cap = StrokeCap.Round)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .fillMaxHeight(.45f)
            ) {
                Text(
                    text = "This Week",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                ColumnChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 22.dp),
                    data = listOf(
                        Bars(
                            label = "Jan",
                            values = listOf(
                                Bars.Data(
                                    label = "Income",
                                    value = 50.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                                Bars.Data(
                                    label = "Expense",
                                    value = 70.0,
                                    color = SolidColor(MaterialTheme.colorScheme.error)
                                ),
                            )
                        ),
                        Bars(
                            label = "Feb",
                            values = listOf(
                                Bars.Data(
                                    label = "Income",
                                    value = 80.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                                Bars.Data(
                                    label = "Expense",
                                    value = 60.0,
                                    color = SolidColor(MaterialTheme.colorScheme.error)
                                ),
                            )
                        ),
                        Bars(
                            label = "Jan",
                            values = listOf(
                                Bars.Data(
                                    label = "Income",
                                    value = 50.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                                Bars.Data(
                                    label = "Expense",
                                    value = 70.0,
                                    color = SolidColor(MaterialTheme.colorScheme.error)
                                ),
                            )
                        ),
                        Bars(
                            label = "Feb",
                            values = listOf(
                                Bars.Data(
                                    label = "Income",
                                    value = 80.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                                Bars.Data(
                                    label = "Expense",
                                    value = 60.0,
                                    color = SolidColor(MaterialTheme.colorScheme.error)
                                ),
                            )
                        ),
                        Bars(
                            label = "Jan",
                            values = listOf(
                                Bars.Data(
                                    label = "Income",
                                    value = 50.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                                Bars.Data(
                                    label = "Expense",
                                    value = 70.0,
                                    color = SolidColor(MaterialTheme.colorScheme.error)
                                ),
                            )
                        ),
                        Bars(
                            label = "Feb",
                            values = listOf(
                                Bars.Data(
                                    label = "Income",
                                    value = 80.0,
                                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                                ),
                                Bars.Data(
                                    label = "Expense",
                                    value = 60.0,
                                    color = SolidColor(MaterialTheme.colorScheme.error)
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
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .fillMaxHeight(.80f)
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
                            label = "Income",
                            values = listOf(
                                117.87,
                                66.49,
                                68.04,
                                134.71,
                                127.0,
                                180.23,
                                158.64,
                                159.14,
                                110.52,
                                67.13,
                                43.48,
                                158.63,
                                67.89,
                                163.13,
                                159.86,
                                68.23,
                                137.48,
                                35.18,
                                73.09,
                                44.98,
                                195.12,
                                155.03,
                                59.62,
                                144.67,
                                195.42,
                                48.95,
                                21.93,
                                147.92,
                                74.42,
                                132.02
                            ),
                            firstGradientFillColor = MaterialTheme.colorScheme.secondary,
                            secondGradientFillColor = MaterialTheme.colorScheme.onSecondary,
                            color = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.onSecondary,
                                    MaterialTheme.colorScheme.secondary
                                ), startY = 0f, endY = 1f
                            ),
                            drawStyle = DrawStyle.Fill,
                            curvedEdges = true
                        ),
                        Line(
                            label = "Linux",
                            values = listOf(
                                167.35,
                                35.21,
                                126.74,
                                173.49,
                                89.32,
                                58.47,
                                109.68,
                                144.12,
                                192.23,
                                63.59,
                                111.97,
                                24.45,
                                150.31,
                                171.76,
                                134.25,
                                46.78,
                                95.14,
                                113.29,
                                187.43,
                                70.58,
                                120.09,
                                138.47,
                                47.56,
                                182.98,
                                57.34,
                                164.71,
                                77.25,
                                199.48,
                                155.39,
                                92.86
                            ),
                            firstGradientFillColor = MaterialTheme.colorScheme.error,
                            secondGradientFillColor = MaterialTheme.colorScheme.errorContainer,
                            color = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.errorContainer,
                                    MaterialTheme.colorScheme.error
                                ), startY = 0f, endY = Float.POSITIVE_INFINITY
                            ),
                            drawStyle = DrawStyle.Fill,
                            curvedEdges = false
                        ),
                    )
                )
            }

        }
    }
}