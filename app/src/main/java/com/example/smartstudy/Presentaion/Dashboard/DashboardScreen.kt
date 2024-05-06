package com.example.smartstudy.Presentaion.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Subjects
import com.example.smartstudy.Domain.Model.Task
import com.example.smartstudy.Presentaion.Components.AddDialog
import com.example.smartstudy.Presentaion.Components.CountCard
import com.example.smartstudy.Presentaion.Components.DeleteDialog
import com.example.smartstudy.Presentaion.Components.SubjectCard
import com.example.smartstudy.Presentaion.Components.studySessionL
import com.example.smartstudy.Presentaion.Components.taskList
import com.example.smartstudy.R
import com.example.smartstudy.util.SnackbarEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun DashboardSreenRoute(navController: NavController){
    val viewModel:DashboardViewModel= hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    val sessions by viewModel.sessions.collectAsStateWithLifecycle(emptyList())

    DashboardScreen(
        event = viewModel::onEvent,
        tasks = tasks,
        snackbarEvent = viewModel._snackbarEventFlow,
        sessions = sessions,
        state = state,
        onSubjectClicked = {
                           navController.navigate("SubjectScreen/${it}"){
                               popUpTo(navController.graph.findStartDestination().id) {
                                   saveState = true
                               }
                               launchSingleTop = true
                               // Restore state when reselecting a previously selected item
                               restoreState = true
                           }
        },
        onTaskClicked = {
            navController.navigate("TaskScreen"){
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
        onSessionClicked = {
            navController.navigate("SessionScreen"){
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
    )
}
@Composable
private fun DashboardScreen(
    event:(DashboardEvent)->Unit,
    state: DashboardState,
    snackbarEvent: SharedFlow<SnackbarEvent>,
    tasks: List<Task>,
    sessions: List<Session>,
    onTaskClicked:(Int)->Unit,
    onSubjectClicked:(Int)->Unit,
    onSessionClicked:()->Unit
){
    var dialogVisablity by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteVisablity by rememberSaveable {
        mutableStateOf(false)
    }

    val snackbarHostState = remember{SnackbarHostState()}


    AddDialog(
        visiblity = dialogVisablity,
        dialogTitle ="Add/Update Subject" ,
        getSubject = state.subjectName,
        getPlannedHour = state.goalStudyHour,
        selectedColor =state.subjectCardColor ,
        onColorChanged = {event(DashboardEvent.onSubjectColorChange(it))},
        onSubjectChanged = { event(DashboardEvent.onSubjectNameChange(it)) },
        onPlanChanged = { event(DashboardEvent.onGoalStudyChange(it)) },
        onConfirmation = {
            event(DashboardEvent.onSaveSubject)
            dialogVisablity=false},
        onDismissRequest = { dialogVisablity = false}
    )
    DeleteDialog(visiblity =deleteVisablity ,
        dialogTitle = "Delete Subject",
        dialogBody = "Are you sure, you want to delete this session? your studied" +
                " session will be reduced by session time. the action cannot be undo",
        onConfirmation = {
            event(DashboardEvent.onDeleteSession)
            deleteVisablity=false},
        onDismissRequest = { deleteVisablity=false })

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest {event->
            when(event){
                is SnackbarEvent.ShowSnackbar ->{
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }

    }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        topBar = {
            TopAppBar()
        }
    ) {paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            item {
                CountSection(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    subjectCount = state.totalSubjectCount,
                    studiedHours = state.totalStudiedHour.toString(),
                    planHour = state.totalGoalHour.toString()
                )
            }
            item{
                SubjectSection(
                    modifier = Modifier.fillMaxSize(),
                    listOfSubject = state.totalSubjects,
                    onSubjectClicked= { onSubjectClicked(it) },
                    addClicked = {dialogVisablity=true}
                )
            }
            item{
                Button(
                    onClick = onSessionClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)

                ) {
                    Text(text = "Start Sudy Session")

                }
            }

            taskList(
                sectionTitle = "UPCOMING TASKS",
                tasks = tasks,
                onCheckClick = {
                               event(DashboardEvent.onSubjectIsCompleteClick(it))
                },
                onTaskClick = {onTaskClicked(it)},
                onEmptyText = "You don't have any upcoming task\n click + to add task"
            )
            item { 
                Spacer(modifier = Modifier.height(10.dp))
            }
            studySessionL(
                sessionTitle = "RECENT STUDY SESSION",
                sessionList = sessions,
                onDelete = {
                    event(DashboardEvent.onDeleteSessionButtonClick(it))
                    deleteVisablity=true},
                onEmptyText = "You don't have any recent study session\n Start study session to record new session")

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(){
    CenterAlignedTopAppBar(
        title = { Text(text = "SmartStudy",
            style = MaterialTheme.typography.headlineMedium) })
}

@Composable
fun CountSection(
    modifier: Modifier,
    subjectCount:Int,
    studiedHours: String,
    planHour: String

){
    Row (
        modifier = modifier
    ){
        CountCard(modifier =Modifier.weight(1f),
            heading = "Subject",
            count = subjectCount.toString()
        )
        Spacer(modifier = Modifier.height(10.dp))
        CountCard(modifier =Modifier.weight(1f),
            heading = "Studied hours",
            count = studiedHours)
        Spacer(modifier = Modifier.height(10.dp))
        CountCard(modifier =Modifier.weight(1f),
            heading = "Planned hours",
            count = planHour)
    }
}

@Composable
fun SubjectSection(
    modifier: Modifier,
    listOfSubject:List<Subjects>,
    addClicked:()->Unit,
    onSubjectClicked:(Int)->Unit,
    onEmptyText:String = "You don't have any subject\n Click + icon to add Subject"
){
    Column (
        modifier = modifier
    ){
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subject",
                style = MaterialTheme.typography.bodySmall
            )
            IconButton(onClick = { addClicked() }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Course"
                )
            }
        }

        if (listOfSubject.isEmpty()){
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_book), 
                contentDescription =onEmptyText
            )
            Text(
                modifier = Modifier.fillMaxSize(),
                text = onEmptyText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center

            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp,end=12.dp)

        ) {
            items(listOfSubject){subject->
                SubjectCard(
                    modifier =Modifier,
                    gradient = subject.colors.map { Color(it) },
                    subjectName = subject.name,
                    onclick = { onSubjectClicked(subject.subjectId) }
                )


            }
        }

        
    }
}