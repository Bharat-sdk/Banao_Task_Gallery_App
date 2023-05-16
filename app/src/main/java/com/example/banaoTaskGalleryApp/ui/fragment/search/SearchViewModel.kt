package com.example.banaoTaskGalleryApp.ui.fragment.search

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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo:BasicRepository
):ViewModel()
{

    @OptIn(FlowPreview::class)
    fun getSearchPhotos(searchQuery:String?): Flow<PagingData<PhotoItem>> = Pager(
        config = PagingConfig(10, enablePlaceholders = false)
    ){
        PhotosPagingDataSource(repo,searchQuery)
    }.flow.cachedIn(viewModelScope).debounce(1200)


}
