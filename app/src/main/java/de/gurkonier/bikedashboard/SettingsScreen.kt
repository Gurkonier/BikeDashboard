package de.gurkonier.bikedashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.gurkonier.bikedashboard.ui.theme.*
import de.gurkonier.bikedashboard.uicomponents.*
import de.gurkonier.bikedashboard.utils.*

object SettingsScreen {
    @Composable
    fun SettingsScreen(onReturn: () -> Unit) {
        val secondsEnabled = remember {
            mutableStateOf(PreferencesManager.secondsEnabled)
        }
        val batteryEnabled = remember {
            mutableStateOf(PreferencesManager.batteryEnabled)
        }
        val dateEnabled = remember {
            mutableStateOf(PreferencesManager.dateEnabled)
        }
        Row(
            Modifier.fillMaxSize()
        ) {
            Column(
                Modifier
                    .padding(32.dp)
                    .width(IntrinsicSize.Max)
            ) {
                ColorSettings()
                PreviewColors(secondsEnabled, batteryEnabled, dateEnabled)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(DividerDefaults.color)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                SwitchSetting(
                    "Sekundenanzeige",
                    secondsEnabled,
                    description = "Die Anzeige von Sekunden\nkann zu einer höheren\nAkkunutzung führen."
                ) {
                    secondsEnabled.value = it
                    PreferencesManager.secondsEnabled = it
                }
                SwitchSetting(
                    "Akkustand",
                    batteryEnabled, topPadding = true
                ) {
                    batteryEnabled.value = it
                    PreferencesManager.batteryEnabled = it
                }
                SwitchSetting(
                    "Datum anzeigen",
                    dateEnabled, topPadding = true
                ) {
                    dateEnabled.value = it
                    PreferencesManager.dateEnabled = it
                }
                Spacer(Modifier.weight(1f))
                Button(onClick = onReturn, Modifier.align(Alignment.End)) {
                    Text(text = "Speichern")
                }
            }
        }
    }

    @Composable
    fun SwitchSetting(
        title: String,
        enabled: MutableState<Boolean>,
        description: String? = null,
        topPadding: Boolean = false,
        onCheckChanged: (Boolean) -> Unit
    ) {
        if (topPadding) {
            Box(
                Modifier.height(8.dp)
            ) {

            }
        }
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                description?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = enabled.value,
                onCheckedChange = onCheckChanged,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }

    @Composable
    fun PreviewColors(
        secondsEnabled: MutableState<Boolean>,
        batteryEnabled: MutableState<Boolean>,
        dateEnabled: MutableState<Boolean>
    ) {
        Text(
            "Vorschau",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.Black)
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
            ) {
                Column(Modifier.align(CenterHorizontally)) {
                    if (dateEnabled.value) {
                        Text(
                            "21.04.2023, Fr.",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                    Text(
                        text =
                        if (secondsEnabled.value) {
                            buildAnnotatedString {
                                append("05")
                                withStyle(style = SpanStyle(color = Color.Green)) {
                                    append(":")
                                }
                                append("45")
                                withStyle(style = SpanStyle(color = Color.Green)) {
                                    append(":")
                                }
                                append("55")
                            }
                        } else {
                            buildAnnotatedString {
                                append("05")
                                withStyle(style = SpanStyle(color = Color.Green)) {
                                    append(":")
                                }
                                append("45")
                            }
                        },
                        fontSize = 64.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(CenterHorizontally)
                    )
                }

                if (batteryEnabled.value) {
                    BorderedProgressIndicator(
                        Modifier
                            .align(CenterHorizontally)
                            .fillMaxWidth(0.6f)
                            .height(32.dp),
                        color = Color.Green,
                        progress = 0.75f,
                        fontSize = 16.sp,
                        strokeThickness = 4.dp,
                        cornerRadius = 8.dp
                    )
                }
            }
//            Box(
//                Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 16.dp)
//            ) {
//                LinearProgressIndicator(
//                    progress = 0.75f,
//                    Modifier
//                        .height(24.dp)
//                        .fillMaxWidth(0.6f),
//                    color = Color.Green
//                )
//                Text(
//
//                    "75 %",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.align(Alignment.Center),
//                    color = Color.Black
//                )
//            }
//
//            Column(
//                Modifier.align(Alignment.Center)
//            ) {
//                Text(
//                    text = "05:45",
//                    fontSize = 64.sp,
//                    color = Color.White
//                )
//                Text(
//                    "21.04.2023, Fr.",
//                    fontSize = 16.sp,
//                    modifier = Modifier.align(CenterHorizontally),
//                    color = Color.White
//                )
//            }
        }
    }

    @Composable
    private fun ColorSettings() {
        Text(
            "Darstellung",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            Modifier.padding(top = 8.dp)
        ) {
            ColorCard("Hintergrund", Color.Black)
            ColorCard("Textfarbe", Color.White, Modifier.padding(start = 16.dp))
        }
    }

    @Composable
    private fun ColorCard(text: String, color: Color, modifier: Modifier = Modifier) {
        Card(modifier) {
            Row(
                Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Box(
                    Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                        .padding(1.dp)
                        .clip(CircleShape)
                        .background(color),
                )
                Text(
                    text = text,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    device = "spec:id=reference_phone,shape=Normal,width=1040,height=480,unit=dp,dpi=512"
)
fun SettingsPreview() {
    BikeDashboardTheme {
        SettingsScreen.SettingsScreen() {}
    }
}