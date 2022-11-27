package com.joekay.module_video.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.joekay.module_video.api.VideoRepo
import com.joekay.module_video.model.Follow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private var videoRepo: VideoRepo
) : ViewModel() {

    fun getPagingData(): Flow<PagingData<Follow.Item>> {
        return videoRepo.getVideoPagingData().cachedIn(viewModelScope)
    }
}