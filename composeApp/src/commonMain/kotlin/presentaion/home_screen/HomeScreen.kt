package presentaion.home_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import presentaion.study_screen.StudyScreen
import presentaion.app_screen.CustomLayout
import presentaion.mcq_screen.McqScreen
import server_mock.Chapter
import kotlin.math.roundToInt

class HomeScreen:Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val settings = koinInject<Settings>()
        val navigator = LocalNavigator.currentOrThrow
        val windowInfo = LocalWindowInfo.current

        val selected = remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Column {
                Text(windowInfo.containerSize.width.toString())
                Button(onClick = {
                    settings.remove("token")
                    navigator.popUntilRoot()
                }){ Text("Logout") }
                LazyColumn {
                    items(Chapter.demoList){chapter ->
                        CustomLayout(modifier = Modifier,chapter.id%2!=0, header = {
                            Box(Modifier
                                .fillMaxWidth()
                                .heightIn(60.dp,90.dp)
                                .background(Color.Green.copy(alpha = 0.65f))){
                                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                    Text(chapter.title)
                                    IconButton(onClick = {}){
                                        Icon(Icons.Filled.List,null)
                                    }
                                }
                            }
                        }, special = {
                            //will unlock after complete the chapter
                            Icon(modifier = Modifier.size(80.dp),
                                imageVector = Icons.Filled.Star,
                                contentDescription = "chap")

                        }){
                            chapter.subjects.forEach {
                                Column (horizontalAlignment = Alignment.CenterHorizontally){
//                                    Icon(modifier = Modifier.size(60.dp), imageVector = Icons.Filled.Star, contentDescription = "chap")
//                                    Text(it)
                                    Box(modifier = Modifier.size(60.dp)){
                                        CustomIconExpanded(modifier = Modifier.size(60.dp),
                                            size = DpSize(60.dp,60.dp), mainContent = {
                                                Column {
                                                    Icon(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(1f).clickable {
                                                        if (selected.value == "${chapter.id} $it"){
                                                            selected.value = ""
                                                        }else{
                                                            selected.value = "${chapter.id} $it"
                                                        }
                                                    },
                                                        imageVector = Icons.Filled.Favorite,
                                                        contentDescription = null)
                                                    Text(it)
                                                }
                                            }, leftContent = {
                                                Column {
                                                    Icon(modifier = Modifier.size(60.dp)
                                                        .clickable {
                                                                 navigator.push(StudyScreen(chapter.id,it))
                                                    },
                                                        imageVector = Icons.Filled.Star,
                                                        contentDescription = null)
                                                    Text("left content")
                                                }
                                            }, isExpanded = {
                                                        selected.value == "${chapter.id} $it"
                                            }, rightContent = {
                                                Column {
                                                    Icon(modifier = Modifier
                                                        .size(60.dp).clickable {
                                                                               navigator.push(McqScreen(
                                                                                   chapter.id,it,0
                                                                               ))
                                                        },
                                                        imageVector = Icons.Filled.Settings,
                                                        contentDescription = null)
                                                    Text("right content")
                                                }
                                            })
                                    }
                                }
                            }
                            Column (horizontalAlignment = Alignment.CenterHorizontally){
                                Icon(modifier = Modifier.size(60.dp), imageVector = Icons.Filled.Star, contentDescription = "chap")
                                Text("Finale Exam")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomIconExpanded(
    modifier: Modifier=Modifier,
    size: DpSize,
    isExpanded:()->Boolean,
    mainContent:@Composable ()->Unit,
    leftContent:@Composable ()->Unit,
    rightContent:@Composable ()->Unit,
) {
    var offset1 by remember { mutableStateOf(Offset.Zero) }
    var offset2 by remember { mutableStateOf(Offset.Zero) }
    val animate1 = animateOffsetAsState(offset1, tween(2000))
    val animate2 = animateOffsetAsState(offset2, tween(2000))
    val sizeInPx = size.width.toPx()

    val sizetoChange: DpSize = if (isExpanded()) size else DpSize.Zero
    val widthAnimate = animateDpAsState(sizetoChange.width, tween(2000))
    val heightAnimate = animateDpAsState(sizetoChange.height, tween(2000))
    val zInd = remember { mutableStateOf(0f) }
    val animateF = animateFloatAsState(zInd.value)
    LaunchedEffect(isExpanded()) {
        if (isExpanded()) {
            offset1 = Offset(x = sizeInPx / 1.8f, y = sizeInPx)
            offset2 = Offset(x = (-sizeInPx / 1.8f), y = sizeInPx)
            zInd.value = 1f
        } else {
            offset1 = Offset.Zero//+Offset(sizeInPx/2,sizeInPx/2)
            offset2 = Offset.Zero//+Offset(sizeInPx/2,sizeInPx/2)
            delay(1500)
            zInd.value = 0f
        }
    }
    Box(modifier = modifier.size(size)) {
        Box(modifier = modifier.size(size)
            .zIndex(1f)) {
            //main content
            mainContent()
        }
        Box(modifier = modifier
            .alpha(animateF.value)
            .width(widthAnimate.value)
            .height(heightAnimate.value)
            .zIndex(animateF.value)
            .absoluteOffset {
                IntOffset(
                    x = animate1.value.x.roundToInt(),
                    y = animate1.value.y.roundToInt()
                )
            }
            .clickable {
                println("Click left")
            }) {
            leftContent()
        }
        Box(modifier = modifier
            .width(widthAnimate.value)
            .height(heightAnimate.value)
            .zIndex(animateF.value)
            .absoluteOffset {
                IntOffset(
                    x = animate2.value.x.roundToInt(),
                    y = animate2.value.y.roundToInt()
                )
            }
            .clickable {
                println("Clicked right")
            }
            .alpha(animateF.value)) {
            rightContent()
        }
    }
}
@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { value * density }
}