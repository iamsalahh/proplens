package com.proplens.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.proplens.android.di.AppContainer
import com.proplens.android.navigation.AppNavGraph

class MainActivity : ComponentActivity() {

    private val appContainer by lazy { AppContainer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    AppNavGraph(appContainer = appContainer)
                }
            }
        }
    }
}
