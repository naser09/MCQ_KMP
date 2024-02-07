import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.example.HockeyPlayer
import core.TestDatabase
import di.commonModule
import di.sharedModule
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import presentaion.splash_screen.SplashScreen

class Test:KoinComponent{
    val testDatabase by inject<TestDatabase>()
    suspend fun getData():List<HockeyPlayer> = testDatabase.get()
}
@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinApplication(application = {
        modules(sharedModule, commonModule)
    }){
        Navigator(SplashScreen())
    }
}
@Composable
fun JsApp() {
    DisposableEffect(Unit){
        startKoin {
            modules(sharedModule, commonModule)
        }
        onDispose {
            stopKoin()
        }
    }
    MainUI()
}
@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainUI() {
    val list = remember {
        mutableStateOf<List<HockeyPlayer>>(emptyList())
    }
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            LaunchedEffect(Unit){
                list.value = Test().getData()
            }
            Button(onClick = {
                showContent = !showContent
            }) {
                Text("Click me!")
            }
            list.value.forEach {
                Column {
                    Text(it.full_name)
                    Text(it.player_number.toString())
                }
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource("compose-multiplatform.xml"), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}