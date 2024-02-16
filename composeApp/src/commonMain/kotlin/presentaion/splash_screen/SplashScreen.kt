package presentaion.splash_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentaion.home_screen.HomeScreen
import presentaion.login_screen.LoginScreen

class SplashScreen : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scale = remember { Animatable(0f) } // Starting scale animation at 0
        val fadeAlpha = remember { Animatable(0f) } // Starting fade animation at 0
        val settings = koinInject<Settings>()
        LaunchedEffect(key1 = true) {
            // Animation sequence with delay
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
            delay(1000) // Adjust this based on your preference (1 second in this case)
            fadeAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 200)
            )
            if (settings.getStringOrNull("token")==null){
                navigator.push(HomeScreen())
            }else{
                navigator.push(LoginScreen())
            }
           // navController.navigate("NextScreen") // Replace with your navigation route
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(res = "compose-multiplatform.xml"), // Replace with your image
                contentDescription = "Splash screen logo",
                modifier = Modifier
                    .size(150.dp) // Adjust size as needed
                    .scale(scale.value) // Apply scale animation
            )
            Text(
                text = "Loading....", // Replace with your app name
                style = MaterialTheme.typography.h5,
                modifier = Modifier.alpha(fadeAlpha.value) // Apply fade animation
            )
        }
    }

}