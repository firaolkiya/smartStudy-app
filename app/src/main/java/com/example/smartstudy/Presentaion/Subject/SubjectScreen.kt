package com.example.smartstudy.Presentaion.Subject

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.smartstudy.Presentaion.Components.AddDialog
import com.example.smartstudy.Presentaion.Components.CountCard
import com.example.smartstudy.Presentaion.Components.DeleteDialog
import com.example.smartstudy.Presentaion.Components.studySessionL
import com.example.smartstudy.Presentaion.Components.taskList
import com.example.smartstudy.Domain.Model.Subjects
import com.example.smartstudy.tasks
import com.example.smartstudy.sessions


@Composable
fun SubjectSreenRoute(navController: NavController){
    val viewModel:SubjectViewModel = hiltViewModel()
    val subjectState by viewModel.subjectState.collectAsStateWithLifecycle()

    SubjectScreen(
        state = subjectState,
        subjectEvent = viewModel::onEvent,
        onbBackClick = {
            navController.navigate("DashboardScreen") {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun  SubjectScreen(
    state: SubjectState,
    subjectEvent: (SubjectEvent)->Unit,
    onbBackClick:()->Unit
){



    val expandList = rememberLazyListState()
    val isExpanded by remember {
        derivedStateOf { expandList.firstVisibleItemIndex==0 }
    }

    var dialogAddVisablity by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteVisablity by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteSubjectVisablity by rememberSaveable {
        mutableStateOf(false)
    }
    val scrollBehavior =TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    AddDialog(
        visiblity = dialogAddVisablity,
        dialogTitle ="Add/Update Subject" ,
        getSubject = state.currentSubjectName,
        getPlannedHour = state.subjectPlannedStudy,
        selectedColor =state.subjectCardColor ,
        onColorChanged = {subjectEvent(SubjectEvent.onSubjectColorChange(it))},
        onSubjectChanged = { subjectEvent(SubjectEvent.onSubjectNameChange(it))},
        onPlanChanged = { subjectEvent(SubjectEvent.onGoalSudyHourChange(it)) },
        onConfirmation = {
            subjectEvent(SubjectEvent.UpdateSubject)
            dialogAddVisablity=false},
        onDismissRequest = { dialogAddVisablity = false}
    )
    DeleteDialog(visiblity =deleteVisablity ,
        dialogTitle = "Delete Session",
        dialogBody = "Are you sure, you want to delete this session? your studied" +
                " session will be reduced by session time. the action cannot be undo",
        onConfirmation = {
            subjectEvent(SubjectEvent.DeleteSession)
            deleteVisablity=false},
        onDismissRequest = { deleteVisablity=false })

    DeleteDialog(visiblity =deleteSubjectVisablity ,
        dialogTitle = "Delete Subject",
        dialogBody = "Are you sure, you want to delete this Subject? This subject" +
                " will be removed. the action cannot be undo",
        onConfirmation = {
            subjectEvent(SubjectEvent.UpdateSubject)
            deleteSubjectVisablity=true},
        onDismissRequest = { deleteSubjectVisablity=false })



    Scaffold(
      modifier = Modifier
          .nestedScroll(scrollBehavior.nestedScrollConnection),
   topBar = { TopSubjectBar(
          subjectTitle = state.currentSubjectName,
          backClicked = onbBackClick,
          deleteClicked = { deleteSubjectVisablity=true},
       scrollBehavior=scrollBehavior,
       editClicked = {dialogAddVisablity=true}
   ) },
      floatingActionButton = {
          ExtendedFloatingActionButton(
              onClick = { /*TODO*/ },
              icon = {Icon(imageVector = Icons.Default.Add,
                  contentDescription = "ADD")},
              text = {Text(text = "Add Task")},
              expanded = isExpanded
          )
      }
  ) {paddingValues ->

      LazyColumn (
          state = expandList,
          modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues)
      ){
          item{
              SubjectOverview(
                  modifier = Modifier
                      .padding(12.dp),
                  progress = state.progress,
                  studiedHours = state.currentSubjectName,
                  planHour = state.subjectPlannedStudy)
          }
          taskList(
              sectionTitle = "UPCOMING TASKS",
              tasks = state.upcomingTask,
              onCheckClick = {subjectEvent(SubjectEvent.onTaskisCompleteClicked(it))},
              onTaskClick = {},
              onEmptyText = "You don't have any upcoming task\n click + to add task"
          )
          item {
              Spacer(modifier = Modifier.height(10.dp))
          }
          taskList(
              sectionTitle = "Completed Task",
              tasks = state.upcomingTask,
              onCheckClick = {subjectEvent(SubjectEvent.onTaskisCompleteClicked(it))},
              onTaskClick = {},
              onEmptyText = "You don't have any Completed task\n Click Task box to Start task"
          )
          item {
              Spacer(modifier = Modifier.height(10.dp))
          }
          studySessionL(
              sessionTitle = "RECENT STUDY SESSION",
              sessionList = state.recentSession,
              onDelete = {
                  subjectEvent(SubjectEvent.onDeleteSessionClicked(it))
                  deleteVisablity=true},
              onEmptyText = "You don't have any recent study session\n Start study session to record new session")


      }

  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSubjectBar(
    subjectTitle:String,
    backClicked:()->Unit,
    deleteClicked:()->Unit,
    editClicked:()->Unit,
    scrollBehavior: TopAppBarScrollBehavior
){
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
                         IconButton(onClick = backClicked) {
                             Icon(
                                 imageVector = Icons.Rounded.ArrowBack,
                                 contentDescription ="Navigate back")
                         }
                        },
        title = { Text(
            text = subjectTitle,
            maxLines = 1,
            style = MaterialTheme.typography.headlineSmall
        )},
        actions = {
            IconButton(onClick = deleteClicked) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription ="Delete Subject" )
            }
            IconButton(onClick = editClicked) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription ="Edit Subject" )
            }
        }
    )
}
@Composable
fun SubjectOverview(
    modifier:Modifier,
    progress:Float,
    studiedHours:String,
    planHour:String

) {
    val progressPercentage= remember(progress) {
        (progress*100).toInt().coerceIn(0,100)
    }
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        CountCard(modifier =Modifier.weight(1f),
            heading = "Planned Study",
            count = studiedHours)
        Spacer(modifier = Modifier.height(10.dp))
        CountCard(modifier =Modifier.weight(1f),
            heading = "Studied hours",
            count = planHour)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 6.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "${progressPercentage}%")
        }

    }
}



