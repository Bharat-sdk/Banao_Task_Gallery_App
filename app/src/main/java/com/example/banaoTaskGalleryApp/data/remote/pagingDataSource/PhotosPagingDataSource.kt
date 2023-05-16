package com.example.banaoTaskGalleryApp.data.remote.pagingDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.banaoTaskGalleryApp.data.remote.repository.BasicRepository
import com.example.banaoTaskGalleryApp.data.remote.response.PhotoItem
import com.example.banaoTaskGalleryApp.utils.network.onError
import com.example.banaoTaskGalleryApp.utils.network.onException
import com.example.banaoTaskGalleryApp.utils.network.onSuccess


class PhotosPagingDataSource(
   private val dataRepository: BasicRepository,
   private val searchKey:String? = null
) : PagingSource<Int, PhotoItem>() {


   companion object {
       private const val STARTING_PAGE_INDEX = 1
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
       val position = params.key ?: STARTING_PAGE_INDEX
       var res: LoadResult<Int,PhotoItem>? = null
       val result = dataRepository.getPhotos(position.toString(),searchKey)

       result.onSuccess {
           res = if (it.stat == "ok") {
                  LoadResult.Page(
                   data = it.photos?.photo ?: emptyList(),
                   prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                   nextKey = if (position > (it.photos?.pages ?: 50)) null else position + 1
               )
           }else{
               LoadResult.Error(Exception(it.message))
           }
       }.onError { _, message ->
          res  = LoadResult.Error(Exception(message))
       }.onException {
         res =  LoadResult.Error(it)
       }
       return res!!

   }

   override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
       // We need to get the previous key (or next key if previous is null) of the page
       // that was closest to the most recently accessed index.
       // Anchor position is the most recently accessed index
       return state.anchorPosition?.let { anchorPosition ->
           state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
               ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
       }
   }
}