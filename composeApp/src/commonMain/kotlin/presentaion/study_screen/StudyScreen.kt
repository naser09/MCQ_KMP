package presentaion.study_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import server_mock.MCQ

class StudyScreen(private val chapterId:Int, private val subject:String):Screen {
    val mcqs = MCQ.demoList.filter { it.chapterId == chapterId && it.subject == subject}
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pagerState = rememberPagerState { mcqs.size }
        println(pagerState.currentPageOffsetFraction)
        LaunchedEffect(pagerState){
            delay(2000)
            pagerState.animateScrollToPage(5)
        }
        val state = remember { mutableStateOf(0) }
        Column {
            Row {
                IconButton(onClick = { state.value = 0}){
                    Icon(Icons.Default.List,null)
                }
                IconButton(onClick = { state.value = 1}){
                    Icon(Icons.Default.Settings,null)
                }
                IconButton(onClick = { state.value = 3}){
                    Icon(Icons.Default.Favorite,null)
                }
            }
            when (state.value) {
                0 -> {
                    HPagerMcq(pagerState)
                }
                1 -> {
                    VPagerMcq(pagerState)
                }
                else -> {
                    StudyMcqList()
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun HPagerMcq(pagerState: PagerState) {
        val scope = rememberCoroutineScope()
        HorizontalPager(pagerState,
            userScrollEnabled = true){page ->
            val mcq = mcqs.get(page)
            val size = remember { mutableStateOf(IntSize.Zero) }
            Box(modifier = Modifier
                .padding(10.dp,5.dp)
                .fillMaxSize(0.90f)
                .clip(RoundedCornerShape(20))
                .background(Color.DarkGray)
                .onPlaced {
                    size.value = it.size
                }
                .onKeyEvent {
                    println(it)
                    println(it.key)
                    println(it.nativeKeyEvent)
                    when(it.key){
                        Key.NavigateNext-> {
                            if (pagerState.canScrollForward){
                                scope.launch {
                                    pagerState.animateScrollToPage(page+1)
                                }
                            }
                        }
                        Key.Spacebar->{
                            if (pagerState.canScrollForward){
                                scope.launch {
                                    pagerState.animateScrollToPage(page+1)
                                }
                            }
                        }
                        Key.NavigatePrevious ->{
                            if (pagerState.canScrollBackward){
                                scope.launch {
                                    pagerState.animateScrollToPage(page-1)
                                }
                            }
                        }
                        else->{}
                    }
                    true
                },
                contentAlignment = Alignment.Center
            ){
                Column {
                    Text(mcq.question, color = Color.White)
                    Text(mcq.explanation, color = Color.White)
                    Text(mcq.answer.first(), color = Color.White)
                }
            }
        }
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun VPagerMcq(pagerState: PagerState) {
        VerticalPager(pagerState){
            val mcq = mcqs.get(it)
            Box(modifier = Modifier
                .padding(10.dp,5.dp)
                .fillMaxSize(0.90f)
                .clip(RoundedCornerShape(20))
                .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ){
                Column {
                    Text(mcq.question, color = Color.White)
                    Text(mcq.explanation, color = Color.White)
                    Text(mcq.answer.first(), color = Color.White)
                }
            }
        }
    }

    @Composable
    private fun StudyMcqList() {
        LazyColumn {
            items(mcqs){mcq ->
                Column(modifier = Modifier.fillMaxSize()
                    .border(width = 1.dp, color = Color.Cyan)){
                    Text("questions : ${mcq.question}")
                    mcq.answer.shuffled().forEach {
                        Button(onClick = {
                            println(mcq.answer.first()==it)
                        }){
                            Text("1 . $it")
                        }
                    }
                }
            }
        }
    }
}