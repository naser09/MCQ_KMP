package server_mock

import core.FakerText

data class Section(
    val id:Int,
    val title: String,
    val description:String,
    val chaptersId:List<Int>
){
    companion object{
        val demolist = listOf(
            Section(0,"First section",FakerText.news, listOf(0,1,2,3,4,5)),
            Section(1,"Second section",FakerText.news, listOf(6,7,8,9)),
        )
    }
}


data class Chapter(
    val id:Int,
    val title:String,
    val unlocked:Boolean,
    val subjects:List<String>
){
    companion object{
        val demoList = ChapterEntity.demoList.mapIndexed { index, chapterEntity ->
            Chapter(
                chapterEntity.id,
                chapterEntity.title,
                chapterEntity.id<5,
                ChapterEntity.subject
            )
        }
    }
}
data class ChapterEntity(
    val id:Int,
    val title:String
){
    companion object{
        private val array = Array(10){ ChapterEntity(it,"Chapter no: $it" )}
        val demoList = array.toList()
        val subject = listOf("Bangla","Math","English","GK")
    }
}
data class MCQ(
    val id:Int,
    val chapterId:Int,
    val subject:String,
    val question:String,
    val explanation:String,
    val answer:List<String>
){
    companion object{
        val demoList = getMcq()
    }
}
private fun getMcq():List<MCQ>{
    var id = 0
    val mutableList = mutableListOf<MCQ>()
    ChapterEntity.demoList.mapIndexed { index, chapter ->
        ChapterEntity.subject.forEach {subject->
            repeat(10){
                val mcq = MCQ(
                    id,
                    index,
                    subject,
                    "Questions For $subject -> Chapter ${chapter.title} .Questions no $id",
                    FakerText.mediumText,
                    listOf("Correct Answer","Wrong 1","Wrong 2","Wrong 3")
                )
                mutableList.add(mcq)
                id++
            }
        }

    }
    return mutableList
}