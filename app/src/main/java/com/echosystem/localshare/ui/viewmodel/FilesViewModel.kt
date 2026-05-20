package com.echosystem.localshare.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.echosystem.localshare.model.SharedFile
import com.echosystem.localshare.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {
    val receivedFiles = fileRepository.files
    
    fun deleteFile(id: String) {
        fileRepository.deleteFile(id)
    }
}
