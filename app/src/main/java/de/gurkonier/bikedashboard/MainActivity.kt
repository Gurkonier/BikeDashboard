package de.gurkonier.bikedashboard

import android.os.Bundle
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
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.gurkonier.bikedashboard.ui.theme.*

var displayCutoutHeight: Int = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var settings by remember {
                mutableStateOf(false)
            }
            BikeDashboardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!settings) {
                        DashboardArea {
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
fun DashboardArea(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            }
            .background(Color.Black)
    ) {
        Box(
            Modifier
                .align(TopCenter)
                .padding(top = 32.dp)
        ){
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
        Column(
            Modifier.align(Center)
        ) {
            Text(
                text = "05:45",
                fontSize = 192.sp,
                color = Color.White
            )
            Text(
                "21.04.2023, Fr.",
                fontSize = 32.sp,
                modifier = Modifier.align(CenterHorizontally),
                color = Color.White
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:id=reference_phone,shape=Normal,width=1040,height=480,unit=dp,dpi=512"
)
@Composable
fun GreetingPreview() {
    BikeDashboardTheme {
        DashboardArea(){}
    }
}