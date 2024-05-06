package com.example.smartstudy.Domain.Model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartstudy.Presentaion.theme.Gradient1
import com.example.smartstudy.Presentaion.theme.Gradient2
import com.example.smartstudy.Presentaion.theme.Gradient3
import com.example.smartstudy.Presentaion.theme.Gradient4
import com.example.smartstudy.Presentaion.theme.Gradient5

@Entity
data class Subjects(
    val name:String,
    val hours:Float,
    val colors: List<Int>,
    @PrimaryKey(autoGenerate = true)
    val subjectId:Int=0
){
    companion object{
        val subjectColor = listOf(Gradient1, Gradient2, Gradient3, Gradient4, Gradient5)
    }
}
