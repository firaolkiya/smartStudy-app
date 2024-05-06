package com.example.smartstudy.Presentaion.SessionPackage

import androidx.lifecycle.ViewModel
import com.example.smartstudy.Domain.Repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    sessionRepository: SessionRepository
): ViewModel() {

}