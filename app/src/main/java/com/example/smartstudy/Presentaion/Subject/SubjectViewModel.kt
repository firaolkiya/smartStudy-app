package com.example.smartstudy.Presentaion.Subject

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartstudy.Domain.Repository.SessionRepository
import com.example.smartstudy.Domain.Repository.SubjectRepository
import com.example.smartstudy.Domain.Repository.TaskRepository
import com.example.smartstudy.Presentaion.Dashboard.DashboardState
import com.example.smartstudy.util.toHour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    val subjectRepository: SubjectRepository,
    val taskRepository: TaskRepository,
    val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val subjectId: MutableStateFlow<String> = MutableStateFlow("0")

    init {
        savedStateHandle.get<String>("subjectId")?.let { id ->
            subjectId.value = id
        }
    }

    public fun IntialID(id: String) {
        subjectId.value = id
    }

    val subjectState = MutableStateFlow(SubjectState())

    val state = combine(
        subjectState,
        taskRepository.getTaskForSubject(subjectId = subjectId.value.toInt()),
        sessionRepository.getTotalSessionDurationForSubject(subjectId = subjectId.value.toInt()),
        taskRepository.getTaskForSubject(subjectId = subjectId.value.toInt()),
        sessionRepository.getRecentSessionForSubject(subjectId = subjectId.value.toInt())
    ) { state, upcomingTask, sessionDuration, completedTask, recentSession ->
        state.copy(
            upcomingTask = upcomingTask,
            completedTask = completedTask,
            recentSession = recentSession,
            subjectStudiedHour = sessionDuration.toHour()
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardState()
        )


    fun onEvent(event: SubjectEvent){
        when(event){
            SubjectEvent.DeleteSession -> TODO()
            SubjectEvent.DeleteSubject -> TODO()
            SubjectEvent.UpdateSubject -> TODO()
            is SubjectEvent.onDeleteSessionClicked -> TODO()
            is SubjectEvent.onGoalSudyHourChange -> TODO()
            is SubjectEvent.onSubjectColorChange -> TODO()
            is SubjectEvent.onSubjectNameChange -> TODO()
            is SubjectEvent.onTaskisCompleteClicked -> TODO()
        }

    }

}