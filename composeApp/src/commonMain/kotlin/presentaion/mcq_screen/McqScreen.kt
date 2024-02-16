package presentaion.mcq_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import server_mock.MCQ

class McqScreen(
    private val chapterId:Int,
    val subject:String,
    val level:Int
):Screen {
    val mcqs = MCQ.demoList.filter { it.chapterId == chapterId && it.subject.lowercase() == subject.lowercase() }
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val lastIndex = remember { mutableStateOf(0) }
        val currentMcq = remember { mutableStateOf(mcqs.first()) }
        val inCurrentMcq = remember { mutableStateListOf<MCQ>() }
        val selectedAnswer = remember { mutableStateOf("") }
        val listOfAns = remember { mutableStateOf(currentMcq.value.answer.shuffled()) }
        BottomSheetNavigator(modifier = Modifier.fillMaxSize(),
            hideOnBackPress = false){navigator ->
            Column {
                Text(currentMcq.value.question)
                Text(currentMcq.value.explanation)
                listOfAns.value.forEach {ans ->
                    Row {
                        Checkbox(checked = selectedAnswer.value == ans,onCheckedChange = {
                            if (it){
                                selectedAnswer.value = ans
                            }else{
                                selectedAnswer.value = ""
                            }
                        })
                        Text(ans)
                    }
                }
                Button(onClick = {
                    println(inCurrentMcq.size)
                    val isCorrect = selectedAnswer.value == currentMcq.value.answer.first()
                    if (!isCorrect){
                        inCurrentMcq.add(currentMcq.value)
                    }
                    navigator.show(ModelSheet(isCorrect){
                        lastIndex.value +=1
                        val getMcq =  mcqs.getOrNull(lastIndex.value)
                        if (getMcq!=null){
                            currentMcq.value = getMcq
                            selectedAnswer.value = ""
                            listOfAns.value = currentMcq.value.answer.shuffled()
                        }else if (inCurrentMcq.isNotEmpty()){
                                println(inCurrentMcq.size)
                                val i = inCurrentMcq.random()
                                currentMcq.value = i
                                selectedAnswer.value = ""
                                listOfAns.value = currentMcq.value.answer.shuffled()
                                inCurrentMcq.remove(i)
                                println("After --- "+inCurrentMcq.size)
                        }else{
                            println("Completed")
                            //todo completed
                        }
                        navigator.hide()
                    })
                }){
                    Text("Check Answer")
                }
            }
        }
    }
}
private class ModelSheet(private val correctAns:Boolean,private val onNext:()->Unit):Screen{
    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .clip(RoundedCornerShape( 15,  15,  0, 0))
            .padding(20.dp)
            .background(if (correctAns) Color.Green else Color.Red)
        ){
            Column {
                Text(if (correctAns) "Correct Ans" else "incorrect")
                Button(onClick = {
                    onNext()
                }){
                    Text("Continue")
                }
            }
        }
    }

}