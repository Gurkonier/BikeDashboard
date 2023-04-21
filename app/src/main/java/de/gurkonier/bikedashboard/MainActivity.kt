package de.gurkonier.bikedashboard

import android.annotation.*
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.gurkonier.bikedashboard.ui.theme.*
import de.gurkonier.bikedashboard.utils.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

var displayCutoutHeight: Int = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesManager.instantiate(getSharedPreferences(getString(R.string.pref_key), MODE_PRIVATE))
        setContent {
            var settings by remember {
                mutableStateOf(false)
            }
            var currentDate = remember{
                mutableStateOf(Date())
            }
            BikeDashboardTheme {
                // A surface container using the 'background' color from the theme
                LaunchedEffect(key1 = "bla", block = {
                    MainScope().launch {
                        while (true){
                            currentDate.value = Date()
                            delay(if(PreferencesManager.secondsEnabled){
                                1000
                            }else{
                                60000
                            })
                        }
                    }
                })
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!settings) {
                        DashboardArea(currentDate) {
                            settings = true
                        }
                    } else {
                        SettingsScreen.SettingsScreen() {
                            settings = false
                        }
                    }
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }
}

@Composable
fun DashboardArea(currentDate: MutableState<Date>, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            }
            .background(Color.Black)
    ) {
        if(PreferencesManager.batteryEnabled) {
            Box(
                Modifier
                    .align(TopCenter)
                    .padding(top = 32.dp)
            ) {
                LinearProgressIndicator(
                    progress = 0.75f,
                    Modifier
                        .height(48.dp)
                        .fillMaxWidth(0.6f),
                    color = Color.Green
                )
                Text(

                    "75 %",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Center),
                    color = Color.Black
                )
            }
        }
        Column(
            Modifier.align(Center)
        ) {
            Text(
                text = SimpleDateFormat(if(PreferencesManager.secondsEnabled){
                    "HH:mm:ss"
                }else{
                    "HH:mm:ss"
                }).format(currentDate.value),
                fontSize = 192.sp,
                color = Color.White
            )
            if (PreferencesManager.dateEnabled) {
                Text(
                    SimpleDateFormat("dd.MM.yyyy, E").format(currentDate.value),
                    fontSize = 32.sp,
                    modifier = Modifier.align(CenterHorizontally),
                    color = Color.White
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    device = "spec:id=reference_phone,shape=Normal,width=1040,height=480,unit=dp,dpi=512"
)
@Composable
fun GreetingPreview() {
    BikeDashboardTheme {
        DashboardArea(mutableStateOf(Date())){}
    }
}