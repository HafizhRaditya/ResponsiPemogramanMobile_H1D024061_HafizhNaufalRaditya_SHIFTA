package com.pemmob1.h1d024061.responsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pemmob1.h1d024061.responsi.ui.navigation.DigimonNavGraph
import com.pemmob1.h1d024061.responsi.ui.theme.DigimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigimonTheme {
                DigimonNavGraph()
            }
        }
    }
}
