package com.example.smartstudy.Presentaion.taskPackage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.smartstudy.Domain.Model.TaskCheckBox
import com.example.smartstudy.Presentaion.Components.DatePickerDialogs
import com.example.smartstudy.Presentaion.Components.DeleteDialog
import com.example.smartstudy.Presentaion.Components.SheetSelecter
import com.example.smartstudy.Presentaion.theme.Green
import com.example.smartstudy.subjects
import com.example.smartstudy.util.ChangeMillisToDateString
import com.example.smartstudy.util.Priority
import kotlinx.coroutines.launch
import java.time.Instant


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskSreenRoute(navController: NavController){
    TaskScreen(
        onBackClicked={
            navController.navigate("DashboardScreen"){
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
        onSaveClicked = {
            navController.navigate("DashboardScreen"){
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreen(
    onBackClicked:()->Unit,
    onSaveClicked:()->Unit
) {

    val viewModel:TaskViewModel= hiltViewModel()

    var scope = rememberCoroutineScope()
    var title by remember {
        mutableStateOf("")
    }
    var taskDescription by remember {
        mutableStateOf("")
    }
    var getTitleError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var onDeleteVisiblty by rememberSaveable {
        mutableStateOf(false)
    }
    var onClickDate by rememberSaveable {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState(
        initialDisplayedMonthMillis = Instant.now().toEpochMilli()
    )
    var sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember{ mutableStateOf(false) }
    DeleteDialog(visiblity =onDeleteVisiblty ,
        dialogTitle = "Delete Subject?",
        dialogBody = "Are you sure, you want to delete this session? your studied" +
                " session will be reduced by session time. the action cannot be undo",
        onConfirmation = {onDeleteVisiblty=false},
        onDismissRequest = { onDeleteVisiblty=false })

    DatePickerDialogs(
        isOpen = onClickDate,
        state = datePickerState,
        onConform = {onClickDate=false},
        onDismiss = { onClickDate=false })
    SheetSelecter(
        isOpen = isSheetOpen,
        subjects = subjects,
        bottomSheetTitle ="Select Subject" ,
        sheetState = sheetState,
        onSubjectCLicked = {
              scope.launch { sheetState.hide() }.invokeOnCompletion {
                  if (!sheetState.isVisible)isSheetOpen=false
              }

        },
        onDismissRequest = { isSheetOpen=false })

    getTitleError = when {
        title.isBlank() -> "Please enter title"
        title.length < 4 -> "Title is too short"
        title.length > 30 -> "Title is too long"
        else -> null
    }
    Scaffold(
        topBar = {
            TaskTopAppBar(
                isExist = true,
                isComplete = false,
                onDeleteCLicked = { onDeleteVisiblty=true },
                onBoxCLicked = { /*TODO*/ },
                checkBoxBorderColor = Green,
                onBackClicked=onBackClicked
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 12.dp),

            ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                maxLines = 1,
                label = { Text(text = "Enter Title") },
                isError = title.isNotBlank() && getTitleError != null,
                supportingText = { Text(text = getTitleError.orEmpty()) }
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(),
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = { Text(text = "Enter Description") },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Due Date", style = MaterialTheme.typography.labelSmall)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = datePickerState
                        .selectedDateMillis
                        .ChangeMillisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(onClick = { onClickDate = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "pick date")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Priority")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Priority.entries.forEach { prio ->
                    PriorityView(
                        modifier = Modifier.weight(1f),
                        backgroundColor = prio.color,
                        borderColor = if (prio == Priority.MEDIUM) Color.White else Color.Transparent,
                        label = prio.title,
                        onClick = {},
                        labelColor = if (prio != Priority.MEDIUM) Color.White.copy(alpha = 0.7f) else Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Select Subject")
            Row(
                modifier = Modifier.fillMaxWidth(),
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
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                enabled = getTitleError == null,
                onClick = onSaveClicked) {
                Text(text = "Save")
            }
        }
        }
    }






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopAppBar(
    isExist:Boolean,
    isComplete:Boolean,
    onDeleteCLicked:()->Unit,
    onBoxCLicked:()->Unit,
    checkBoxBorderColor: androidx.compose.ui.graphics.Color,
    onBackClicked:()->Unit

    )
{
    TopAppBar(
        title = {
            Text(
                text = "Task", style = MaterialTheme.typography.headlineSmall)
            },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigation Back"
                )
            }
        },
        actions = {
            if (isExist) {
                TaskCheckBox(
                    borderColor = checkBoxBorderColor,
                    onCLick = onBoxCLicked,
                    isComplete = isComplete
                )
            }
            IconButton(
                onClick = onDeleteCLicked) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription ="Delete Task" )
            }
        }
    )
}

@Composable
fun PriorityView(
    modifier: Modifier,
    backgroundColor: Color,
    borderColor:Color,
    label:String,
    labelColor:Color,
    onClick:()->Unit

    ){
    Box (
        modifier = modifier
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(5.dp)
            .border(2.dp, borderColor, CircleShape)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Text(text = label, color = labelColor)

    }
}