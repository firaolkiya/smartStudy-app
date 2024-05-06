package com.example.smartstudy.Presentaion.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.smartstudy.Domain.Model.TaskCheckBox
import com.example.smartstudy.Domain.Model.Task
import com.example.smartstudy.R
import com.example.smartstudy.util.Priority

fun LazyListScope.taskList(
    sectionTitle:String,
    tasks:List<Task>,
    onEmptyText:String,
    onTaskClick:(Int)->Unit,
    onCheckClick:(Task)->Unit
){
    item {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(12.dp)
            )
            if (tasks.isEmpty()) {
                Image(
                    modifier = Modifier
                        .size(120.dp),
                    painter = painterResource(id = R.drawable.img_tasks),
                    contentDescription = onEmptyText
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = onEmptyText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center

                )
            }

        }
    }

        items(tasks){task->
                TaskCard(modifier = Modifier
                    .padding(start = 12.dp,end=4.dp),
                    task = task,
                    onTaskClick = { onTaskClick(task.taskId)},
                    onCheckClick = {onCheckClick(task)}
                )
            }

}
@Composable
private fun TaskCard(
    modifier: Modifier,
    task: Task,
    onTaskClick:()->Unit,
    onCheckClick:()->Unit
){
    ElevatedCard(
        modifier = modifier.clickable { onTaskClick() }
    )
        {

        Row (
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TaskCheckBox(borderColor = Priority.fromInt(task.priority).color,
                onCLick = { onCheckClick()},
                isComplete = task.isCompleted
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (task.isCompleted) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${task.dueDate}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        }

}