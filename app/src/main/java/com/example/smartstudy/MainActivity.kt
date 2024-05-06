package com.example.smartstudy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smartstudy.Presentaion.Dashboard.DashboardSreenRoute
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Subjects
import com.example.smartstudy.Domain.Model.Task
import com.example.smartstudy.Presentaion.SessionPackage.SessionSreenRoute
import com.example.smartstudy.Presentaion.Subject.SubjectSreenRoute
import com.example.smartstudy.Presentaion.Subject.SubjectViewModel
import com.example.smartstudy.Presentaion.taskPackage.TaskSreenRoute
import com.example.smartstudy.Presentaion.theme.SmartStudyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "DashboardScreen"
                    ){
                        composable("DashboardScreen"){
                            DashboardSreenRoute(navController)
                        }
                        composable("TaskScreen"){
                            TaskSreenRoute(navController)
                        }
                        composable("SubjectScreen/{subjectId}",
                            arguments = listOf(navArgument("subjectId")
                            { type = NavType.StringType })){
                            val id=it.arguments?.getString("subjectId")?:0
                            SubjectSreenRoute(navController)
                        }
                        composable("SessionScreen"){
                            SessionSreenRoute(navController)
                        }
                    }
                }
            }
        }
    }
}

val subjects = listOf(
    Subjects(
        name = "Python",
        hours = 10f,
        colors = Subjects.subjectColor[0].map { it.toArgb() },
        subjectId = 1
    ),
    Subjects(
        name = "Web app",
        hours = 10f,
        colors =  Subjects.subjectColor[0].map { it.toArgb() },
        subjectId = 2
    ),
    Subjects(
        name = "Java",
        hours = 20f,
        colors = Subjects.subjectColor[0].map { it.toArgb() },
        subjectId = 3
    ),
    Subjects(
        name = "Python",
        hours = 15f,
        colors = Subjects.subjectColor[0].map { it.toArgb() },
        subjectId = 4
    ),
    Subjects(
        name = "C++",
        hours = 13f,
        colors = Subjects.subjectColor[0].map { it.toArgb() },
        subjectId = 5
    )

)
val tasks = listOf(
    Task(
        title = "problem solving",
        description = "i will work on leetcode",
        isCompleted = false,
        dueDate = 15,
        priority =3,
        relatedSubject = "C++",
        taskId = 1,
        taskSubjectId = 1
    ),
    Task(
        title = "Development",
        description = "i will develop mobile app",
        isCompleted = false,
        dueDate = 500,
        priority = 2,
        relatedSubject = "Kotlin",
        taskId = 2,
        taskSubjectId = 2
    ), Task(
        title = "Study class courses",
        description = "i will study for mid exam",
        isCompleted = true,
        dueDate = 15,
        priority = 1,
        relatedSubject = "Java",
        taskId = 3,
        taskSubjectId = 1
    ),
    Task(
        title = "A2SV hub working",
        description = "i will work from hub",
        isCompleted = true,
        dueDate = 15,
        priority = 2,
        relatedSubject = "Python",
        taskId = 4,
        taskSubjectId = 3
    )
)
val sessions = listOf(
    Session(sessionID = 1,
        relatedSubjects = "Advanced Programming",
        sessionSubjectId = 5,
        date = 1000000,
        duration = 15000
    ),
    Session(sessionID = 2,
        relatedSubjects = "Java",
        sessionSubjectId = 1,
        date = 1000000,
        duration = 15000
    ),
    Session(sessionID = 3,
        relatedSubjects = "Kotlin",
        sessionSubjectId = 2,
        date = 1000000,
        duration = 15000
    ),
    Session(sessionID = 4,
        relatedSubjects = "Python",
        sessionSubjectId = 3,
        date = 1000000,
        duration = 15000
    ),
    Session(sessionID = 5,
        relatedSubjects = "C++",
        sessionSubjectId = 4,
        date = 1000000,
        duration = 15000
    )
)
