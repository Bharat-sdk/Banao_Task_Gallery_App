package com.example.banaoTaskGalleryApp.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.banaoTaskGalleryApp.data.remote.pagingDataSource.PhotosPagingDataSource
import com.example.banaoTaskGalleryApp.data.remote.repository.BasicRepository
import com.example.banaoTaskGalleryApp.data.remote.response.PhotoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo:BasicRepository
):ViewModel()
{


    fun getRecentPhotos(): Flow<PagingData<PhotoItem>> = Pager(
        config = PagingConfig(10, enablePlaceholders = false)
    ){
        PhotosPagingDataSource(repo)
    }.flow.cachedIn(viewModelScope)
    
}
