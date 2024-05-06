package com.example.smartstudy.util
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import com.example.smartstudy.Presentaion.theme.Orange
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


enum class Priority(
    val title:String,
    val color: Color,
    val value:Int
){
    LOW(title = "low", color = Color.Green, value =0),
    MEDIUM(title = "medium", color = Orange, value =1),
    HIGH(title = "high", color = Color.Red,value =2);

    companion object{
        fun fromInt(value:Int)= entries.firstOrNull{it.value==value}?: MEDIUM
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long?.ChangeMillisToDateString(): String{
    val date:LocalDate=this?.let{
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }?:LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MM yy"))
    }
fun Long.toHour():Float{
    val hour = this.toFloat()//3600f
    return "%.2f".format(hour).toFloat()
}

sealed class SnackbarEvent(){

    data class ShowSnackbar(
        val message:String,
        val duration:SnackbarDuration=SnackbarDuration.Short
    ):SnackbarEvent()
}

