package com.example.smartstudy.Data.local

import androidx.room.TypeConverter

class ColorConverter {
    @TypeConverter
    fun listOfColorToString(colorList:List<Int>): String {
        return colorList.joinToString(","){ it.toString() }
    }

    @TypeConverter
    fun stringToListOfColor(colorString: String):List<Int>{
        return colorString.split(",").map{it.toInt()}
    }
}