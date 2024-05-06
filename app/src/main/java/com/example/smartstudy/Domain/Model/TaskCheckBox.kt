package com.example.smartstudy.Domain.Model

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskCheckBox(
     borderColor:Color,
     onCLick:()->Unit,
     isComplete:Boolean
) {
    Box (
        modifier = Modifier
            .size(25.dp)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .clickable { onCLick() },
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility(visible =isComplete ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription ="Completed" )

        }

    }

    
}