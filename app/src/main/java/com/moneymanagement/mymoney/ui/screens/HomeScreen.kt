package com.moneymanagement.mymoney.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.R
import com.moneymanagement.mymoney.db.Wallet
import com.moneymanagement.mymoney.navigation.BottomNavItems
import com.moneymanagement.mymoney.ui.components.BottomNavigationBar
import com.moneymanagement.mymoney.ui.components.CustomButton
import com.moneymanagement.mymoney.ui.components.CustomEditText
import com.moneymanagement.mymoney.ui.components.TransactionRow
import com.moneymanagement.mymoney.ui.components.WalletUI


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    homeScreenViewmodel: HomeScreenViewmodel
) {

    val scope = rememberCoroutineScope()
    val showBottomSheet by homeScreenViewmodel.openBottomSheet.collectAsState()


    val wallets by homeScreenViewmodel.wallets.collectAsState()
    val recentTransactions by homeScreenViewmodel.recentTransactions.collectAsState()
    val totalBalance by homeScreenViewmodel.totalBalance.collectAsState()
    val thisMonthExpense by homeScreenViewmodel.thisMonthExpense.collectAsState()
    val thisMonthIncome by homeScreenViewmodel.thisMonthIncome.collectAsState()

    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) {
        val pagerState = rememberPagerState(initialPage = 0, pageCount = {wallets.size})
        //homeScreenViewmodel.fetchWallet()
        //homeScreenViewmodel.fetchCurrentMonthTransactions()
        //homeScreenViewmodel.calculateTotalBalance()
        //homeScreenViewmodel.calculateThisMonthIncome()
        //homeScreenViewmodel.calculateThisMonthExpenses()
        //homeScreenViewmodel.unReadSMS()
        //homeScreenViewmodel.smsAnalysis()
        //homeScreenViewmodel.recentTransactions()

        LaunchedEffect(true) {
            homeScreenViewmodel.fetchData()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.25f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.6f)
                        .background(color = MaterialTheme.colorScheme.primary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(top = 16.dp, bottom = 16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_person_24),
                                contentDescription = "User Image",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier.height(50.dp),
                                horizontalAlignment = AbsoluteAlignment.Left
                            ) {
                                Text(
                                    text = "Total Balance",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    text = "$ $totalBalance",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .fillMaxHeight()
                        .offset(y = -40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceBright,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp),
                    horizontalAlignment = AbsoluteAlignment.Left,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Monthly Balance",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$$thisMonthExpense/$$thisMonthIncome",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${thisMonthExpense / thisMonthIncome}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    LinearProgressIndicator(
                        progress = { (thisMonthExpense / thisMonthIncome + thisMonthExpense).toFloat() },
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.error,
                        trackColor = Color.Green
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .fillMaxHeight(.25f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Wallets",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Add New One",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            homeScreenViewmodel.openBottomSheet()
                        }
                    )
                }
                if (wallets.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomButton(
                            title = "Add New Wallet",
                            textColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.fillMaxWidth(.9f)
                        ) {
                            homeScreenViewmodel.openBottomSheet()
                        }
                    }
                }
                else{
                    HorizontalPager(
                        state = pagerState,
                        pageSize = PageSize.Fixed(300.dp),
                        pageSpacing = 20.dp
                    ) {page->
                            WalletUI(wallets[page])
                    }
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .fillMaxHeight(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Transactions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "See All",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            navController.navigate(BottomNavItems.Transaction.route)
                        }
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
                Text(
                    text = "recent",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .verticalScroll(state = rememberScrollState())
                ) {
                    if(recentTransactions.isEmpty()){
                        Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("No Transaction Found")
                        }

                    }
                    else{
                       recentTransactions.forEach {transaction->
                           val wallet = wallets.firstOrNull { it.id == transaction.walletId }
                           if(wallet != null){
                               TransactionRow(transaction,wallet)
                           }
                           Spacer(modifier = Modifier.height(8.dp))
                       }
                    }
                }
            }

        }
        if (showBottomSheet) {
            WalletBottomSheet(homeScreenViewmodel = homeScreenViewmodel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletBottomSheet(homeScreenViewmodel: HomeScreenViewmodel){
    val sheetState = rememberModalBottomSheetState()
    val modifier = Modifier.fillMaxWidth(.9f)
    var walletName by remember {
        mutableStateOf("")
    }
    var lastDigit by remember {
        mutableStateOf("")
    }
    var balance by remember {
        mutableDoubleStateOf(0.0)
    }
    var walletNameError by remember {
        mutableStateOf<String?>(null)
    }
    var lastDigitError by remember {
        mutableStateOf<String?>(null)
    }
    var balanceError by remember {
        mutableStateOf<String?>(null)
    }
    ModalBottomSheet(
        onDismissRequest = {
            homeScreenViewmodel.closeBottomSheet()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Wallet Name",
                modifier = modifier,
                keyboardType = KeyboardType.Text,
                errorMessage = walletNameError,
                onValueChanged = { value, error ->
                    walletName = value
                    walletNameError = error
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Identifier",
                modifier = modifier,
                keyboardType = KeyboardType.Text,
                errorMessage = lastDigitError,
                onValueChanged = { value, error ->
                    lastDigit = value
                    lastDigitError = error
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Balance",
                modifier = modifier,
                keyboardType = KeyboardType.Decimal,
                errorMessage = balanceError,
                onValueChanged = { value, error ->
                    balance = value.toDouble()
                    balanceError = error
                })
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                title = "Add", textColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth(.9f)
            ) {
                homeScreenViewmodel.insertWallet(Wallet(name = walletName, lastDigits = lastDigit, balance = balance))
                homeScreenViewmodel.closeBottomSheet()
            }
        }

    }
}
