package com.vignesh.pcl.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TournamentViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TournamentViewModel::class.java)) {
            return TournamentViewModel(application) as T
        }
        throw IllegalArgumentException("Uknown ViewModel Class")
    }
}