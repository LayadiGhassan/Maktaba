package com.ElOuedUniv.maktaba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ElOuedUniv.maktaba.data.local.UserPreferences
import com.ElOuedUniv.maktaba.presentation.navigation.NavGraph
import com.ElOuedUniv.maktaba.presentation.theme.MaktabaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    
    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            
            val isOnboardingCompleted by userPreferences.isOnboardingCompleted
                .collectAsState(initial = null)

            MaktabaTheme {
                
                if (isOnboardingCompleted != null) {
                    NavGraph(isOnboardingCompleted = isOnboardingCompleted ?: false)
                }
            }
        }
    }
}
