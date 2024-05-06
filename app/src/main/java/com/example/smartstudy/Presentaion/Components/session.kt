package com.example.smartstudy.Presentaion.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.R

fun LazyListScope.studySessionL(
    sessionTitle: String,
    sessionList:List<Session>,
    onEmptyText:String,
    onDelete: (Session)->Unit
){
    item {

            Text(
                modifier = Modifier.padding(12.dp),
                text = sessionTitle,
                style = MaterialTheme.typography.bodySmall
            )
            if (sessionList.isEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Image(
                    modifier = Modifier
                        .size(120.dp),
                    painter = painterResource(id = R.drawable.img_lamp),
                    contentDescription = sessionTitle
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = onEmptyText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center

                )
            }
        }
    }
    items(sessionList){session->
        SessionCard(modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp),
            session =session,
            onDelete = {onDelete(session)}
        )
    }

}

@Composable
fun SessionCard(
    modifier:Modifier,
    session: Session,
    onDelete:()->Unit
){
    Card (
        modifier=modifier
    ){
        Row(
            modifier= Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = modifier.padding(10.dp)
            ){

                Text(
                    text = session.relatedSubjects,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${session.date}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${session.duration} hr",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = { onDelete()} ){
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription ="delete session" )
            }
        }
    }

}