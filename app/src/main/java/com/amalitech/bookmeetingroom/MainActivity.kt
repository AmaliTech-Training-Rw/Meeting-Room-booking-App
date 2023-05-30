package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amalitech.bookmeetingroom.navigation.Route
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.onboarding.OnboardingScreen
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences
import com.amalitech.onboarding.splash_screen.SplashScreen
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val sharedPref: OnboardingSharedPreferences by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMeetingRoomTheme {
                val shouldShowOnboarding = sharedPref.loadShouldShowOnboarding()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = if(shouldShowOnboarding)
                    Route.ONBOARDING else
                        Route.SPLASH
                ) {
                    composable(Route.ONBOARDING) {
                        OnboardingScreen(
                            onNavigateToLogin = {}
                        )
                    }
                    composable(Route.SPLASH) {
                        SplashScreen(onNavigate = { isAdmin ->
                            if (isAdmin) {
                                // TODO(Navigate to admin dashboard)
                            } else {
                                // TODO(Navigate to home screen)
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookMeetingRoomTheme {
        Greeting("Android")
    }
}
