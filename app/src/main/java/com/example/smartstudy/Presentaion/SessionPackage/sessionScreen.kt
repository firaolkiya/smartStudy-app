package com.example.smartstudy.Presentaion.SessionPackage

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.smartstudy.Presentaion.Components.DeleteDialog
import com.example.smartstudy.Presentaion.Components.SheetSelecter
import com.example.smartstudy.Presentaion.Components.studySessionL
import com.example.smartstudy.sessions
import com.example.smartstudy.subjects
import kotlinx.coroutines.launch


@Composable
fun SessionSreenRoute(navController: NavController){
    SessionScreen(onBackClick = {
        navController.navigate("DashboardScreen"){
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    onBackClick: () -> Unit
){

    val viewModel: SessionViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    var isSheetOpen by remember { mutableStateOf(false) }
    var subjectSheetSate= rememberModalBottomSheetState()

    var historyDelete by remember { mutableStateOf(false) }
    DeleteDialog(visiblity =historyDelete ,
        dialogTitle = "Delete Session?",
        dialogBody = "Are you sure, you want to delete this session? your studied" +
                " session will be reduced by session time. the action cannot be undo",
        onConfirmation = {historyDelete=false},
        onDismissRequest = { historyDelete=false })

    SheetSelecter(
        isOpen = isSheetOpen,
        subjects = subjects,
        bottomSheetTitle ="Select Subject" ,
        sheetState = subjectSheetSate,
        onSubjectCLicked = {
            scope.launch { subjectSheetSate.hide() }.invokeOnCompletion {
                if (!subjectSheetSate.isVisible)isSheetOpen=false
            }

        },
        onDismissRequest = { isSheetOpen=false })

    Scaffold(
        topBar = {
            SessionTopAppBar(onBackClick = onBackClick)
        }
    ) {paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            item{
                TimeCounter(modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    , counterText ="0:0:56" )
            }
            item{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    Text(text = "Select Subject")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "English", style = MaterialTheme.typography.bodyLarge)
                        IconButton(onClick = { isSheetOpen = true }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "select subject"
                            )
                        }
                    }
                }
            }
            item { 
                Spacer(modifier = Modifier.height(10.dp))
            }
            item{
                ButtonSection(onStartClicked = { /*TODO*/ },
                    onFinishClicked = {},
                    onStopClicked = { /*TODO*/ })
                    
                }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            studySessionL(
                sessionTitle = "STUDY SESSION HISTORY",
                sessionList = sessions,
                onDelete = {historyDelete=true},
                onEmptyText = "You don't have any recent study session\n Start study session to record new session")


        }
            }


        }
        


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionTopAppBar(onBackClick:()->Unit){
    TopAppBar(
        navigationIcon = { IconButton(
            onClick = onBackClick)
            { Icon(imageVector =Icons.Rounded.ArrowBack,
            contentDescription ="Navigate Back") }
             },
            title = { Text(text = "Study Session",
                style = MaterialTheme.typography.headlineLarge)
            }
    )
}

@Composable
fun TimeCounter(
    modifier: Modifier,
    counterText:String
){
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(
                    6.dp,
                    MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                ),
        )
        Text(
            text = counterText,
            style = MaterialTheme.typography
                .titleLarge.copy(fontSize = 45.sp)
        )
    }

}
@Composable
fun ButtonSection(
    onStartClicked:()->Unit,
    onStopClicked:()->Unit,
    onFinishClicked:()->Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
            onClick = onStopClicked
        ) {
            Text(text = "Stop")
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
            onClick = onStartClicked
        ) {
            Text(text = "Start")
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
            onClick = onFinishClicked
        ) {
            Text(text = "Finish")
        }
    }
}