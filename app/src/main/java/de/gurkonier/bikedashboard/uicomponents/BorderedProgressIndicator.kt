package de.gurkonier.bikedashboard.uicomponents

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import kotlin.math.*

@Composable
fun BorderedProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    color: Color = Color.Green,
    fontSize: TextUnit = 24.sp
) {
    Box(
        modifier
            .height(48.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .border(6.dp, Color.Gray, RoundedCornerShape(12.dp))
        )
        Box(
            Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .border(6.dp, color, RoundedCornerShape(12.dp))
        )
        Text(
            "${(progress * 100).roundToInt()} %",
            Modifier.align(Alignment.Center),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    }
}

@Composable
@Preview
fun BPIPreview() {
    BorderedProgressIndicator(
        Modifier.size(256.dp, 48.dp),
        progress = 0.666f
    )
}