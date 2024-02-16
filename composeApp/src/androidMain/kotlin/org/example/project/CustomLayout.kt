package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    reverse:Boolean=false,
    header:@Composable ()->Unit,
    special:@Composable ()->Unit,
    items:@Composable ()->Unit
) {
    Layout(contents = listOf(header,special,items), modifier = modifier){
            (headerMeasure,specialMeasure,itemsMeasure),constrains->
        require(headerMeasure.size<=1){ "Header be at most 1 item" }
        require(specialMeasure.size<=1){ "Special be at most 1 item" }
        val headerPlaceable = headerMeasure.first().measure(constrains)
        val specialPlaceable = specialMeasure.first().measure(constrains)
        val itemsPlaceable = itemsMeasure.map { it.measure(constrains) }

        val totalHeight = headerPlaceable.measuredHeight+itemsPlaceable.sumOf { it.height }
        var height = headerPlaceable.height
        val middleY4Special = itemsPlaceable.sumOf { it.height }/2+(specialPlaceable.height/2)
        val perItemInc = (headerPlaceable.width/2)/(itemsPlaceable.size/2)
        var startX = headerPlaceable.width/2 - (itemsPlaceable.first().width/2)
        layout(width = headerPlaceable.width, height = totalHeight){
            headerPlaceable.place(x = 0, y = 0)
            if (reverse){
                specialPlaceable.place(x = headerPlaceable.width-(specialPlaceable.width+specialPlaceable.width/2),
                    y = middleY4Special)
            }else{
                specialPlaceable.place(x = specialPlaceable.width/2, y = middleY4Special)
            }
            itemsPlaceable.forEachIndexed { index, placeable ->
                if (index>itemsPlaceable.size/2-1){
                    placeable.place(x=startX,y=height)
                    height+=placeable.height
                    if (reverse){
                        startX+=(perItemInc*0.75f).toInt()
                    }else{
                        startX-=(perItemInc*0.75f).toInt()
                    }
                }else{
                    placeable.place(x=startX,y=height)
                    height += placeable.height
                    if (reverse){
                        startX -= (perItemInc * 0.75f).toInt()
                    }else {
                        startX += (perItemInc * 0.75f).toInt()
                    }
                }
                startX = startX.coerceIn(0,headerPlaceable.width)
            }
        }
    }
}
