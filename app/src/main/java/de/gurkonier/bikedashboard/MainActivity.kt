package de.gurkonier.bikedashboard

import android.annotation.*
import android.content.*
import android.os.*
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.gurkonier.bikedashboard.receiver.*
import de.gurkonier.bikedashboard.ui.theme.*
import de.gurkonier.bikedashboard.uicomponents.*
import de.gurkonier.bikedashboard.utils.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

var displayCutoutHeight: Int = 0

val sdfHours = SimpleDateFormat("HH")
val sdfMinutes = SimpleDateFormat("mm")
val sdfSeconds = SimpleDateFormat("ss")
val sdfDate = SimpleDateFormat("dd.MM.yyyy, E")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesManager.instantiate(
            getSharedPreferences(
                getString(R.string.pref_key),
                MODE_PRIVATE
            )
        )
        setContent {

            var settings by remember {
                mutableStateOf(false)
            }
            var currentDate = remember {
                mutableStateOf(Date())
            }
            var level by remember {
                mutableStateOf(0)
            }
            registerReceiver(BatteryLevelReceiver(object :
                BatteryLevelReceiver.BatteryLevelListener {
                override fun onLevelUpdated(newLevel: Int) {
                    level = newLevel
                    Log.d("BATTERY_STATUS", "New level received: $newLevel")
                }

            }),
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            BikeDashboardTheme {
                // A surface container using the 'background' color from the theme
                LaunchedEffect(key1 = "bla", block = {
                    MainScope().launch {
                        while (true) {
                            currentDate.value = Date()
                            delay(
                                if (PreferencesManager.secondsEnabled) {
                                    1000
                                } else {
                                    60000
                                }
                            )
                        }
                    }
                })
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!settings) {
                        //TODO: Add battery percentage functionality
                        DashboardArea(currentDate, batteryLevel = level/100f) {
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
}

@Composable
fun DashboardArea(
    currentDate: MutableState<Date>,
    batteryLevel: Float = 0.75f,
    debug: Boolean = false,
    onClick: () -> Unit
) {
    val batIndColor = if (batteryLevel > 0.2f) Color.Green else Color.Red
    Box(
        Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            }
            .background(Color.Black)
    ) {
        Column(
            Modifier
                .align(Center)
        ) {
            Column(Modifier.align(CenterHorizontally)) {
                if (PreferencesManager.dateEnabled || debug) {
                    Text(
                        sdfDate.format(currentDate.value),
                        fontSize = 32.sp,
                        color = Color.White
                    )
                }
                Text(
                    text =
                    if (PreferencesManager.secondsEnabled) {
                        buildAnnotatedString {
                            append(sdfHours.format(currentDate.value))
                            withStyle(style = SpanStyle(color = batIndColor)) {
                                append(":")
                            }
                            append(sdfMinutes.format(currentDate.value))
                            withStyle(style = SpanStyle(color = batIndColor)) {
                                append(":")
                            }
                            append(sdfSeconds.format(currentDate.value))
                        }
                    } else {
                        buildAnnotatedString {
                            append(sdfHours.format(currentDate.value))
                            withStyle(style = SpanStyle(color = batIndColor)) {
                                append(":")
                            }
                            append(sdfMinutes.format(currentDate.value))
                        }
                    },
                    fontSize = 192.nonScaledSp,
                    color = Color.White,
                    modifier = Modifier
                        .align(CenterHorizontally)
                )
            }

            if (PreferencesManager.batteryEnabled || debug) {
                BorderedProgressIndicator(
                    Modifier
                        .align(CenterHorizontally)
                        .fillMaxWidth(0.6f),
                    color = batIndColor,
                    progress = batteryLevel
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showBackground = true,
    device = "spec:parent=pixel_7_pro,orientation=landscape", name = "Dashboard"
)
@Preview(
    showBackground = true,
    device = "spec:parent=pixel_7_pro,orientation=landscape", fontScale = 1.5f,
    name = "Dashboard - FontScale: 1.5"
)
@Composable
fun GreetingPreview() {
    BikeDashboardTheme {
        DashboardArea(mutableStateOf(Date()), batteryLevel = 0.2f, debug = true) {}
    }
}

val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp