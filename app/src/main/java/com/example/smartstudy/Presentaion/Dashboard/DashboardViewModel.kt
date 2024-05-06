package com.example.smartstudy.Presentaion.Dashboard

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartstudy.Domain.Model.Session
import com.example.smartstudy.Domain.Model.Subjects
import com.example.smartstudy.Domain.Model.Task
import com.example.smartstudy.Domain.Repository.SessionRepository
import com.example.smartstudy.Domain.Repository.SubjectRepository
import com.example.smartstudy.Domain.Repository.TaskRepository
import com.example.smartstudy.util.SnackbarEvent
import com.example.smartstudy.util.toHour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
   private val subjectRepository: SubjectRepository,
   private val sessionRepository: SessionRepository,
   private val taskRepository: TaskRepository
):  ViewModel() {

    private val dashboardState = MutableStateFlow(DashboardState())

    val state = combine(
        dashboardState,
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHour(),
        subjectRepository.getListOfAllSubject(),
        sessionRepository.getTotalSessionDuration()
    ){d_state,subjectCount,goalHour,allSubject,sessionDuration->

        d_state.copy(
            totalSubjectCount = subjectCount,
            totalGoalHour = goalHour,
            totalStudiedHour = sessionDuration.toHour(),
            totalSubjects = allSubject
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )

    val tasks:Flow<List<Task>> = taskRepository.getAllUpcomingTask()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val sessions:Flow<List<Session>> = sessionRepository.getAllUpcomingSession()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val _snackbarEventFlow=snackbarEventFlow.asSharedFlow()

    fun onEvent(event: DashboardEvent){
        when(event){
            is DashboardEvent.onDeleteSessionButtonClick -> {
                dashboardState.update {
                    it.copy(session = event.session)
                }
            }
            DashboardEvent.onDeleteSession ->{}
            is DashboardEvent.onGoalStudyChange -> {
                dashboardState.update {
                    it.copy(goalStudyHour = event.goal)
                }
            }
            DashboardEvent.onSaveSubject -> onSave()
            is DashboardEvent.onSubjectColorChange -> {
                dashboardState.update {
                    it.copy(subjectCardColor = event.color)
                }
            }
            is DashboardEvent.onSubjectIsCompleteClick -> TODO()
            is DashboardEvent.onSubjectNameChange -> {
                dashboardState.update {
                    it.copy(subjectName = event.name)
                }
            }
        }

    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                subjectRepository.Upsert(
                    Subjects(
                        state.value.subjectName,
                        state.value.goalStudyHour.toFloat(),
                        state.value.subjectCardColor.map { it.toArgb() }
                    )
                )
                snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar("Saved Successfully")
                )
            }
            catch (e:Exception){
               snackbarEventFlow.emit(
                   SnackbarEvent.ShowSnackbar("Couldn't save subject",SnackbarDuration.Long),

               )
            }
        }
    }


}

