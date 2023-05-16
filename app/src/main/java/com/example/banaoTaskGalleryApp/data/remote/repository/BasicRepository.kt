package com.example.banaoTaskGalleryApp.data.remote.repository

import com.example.banaoTaskGalleryApp.data.remote.api.BasicApiInterface
import com.example.banaoTaskGalleryApp.data.remote.response.PhotosResponse
import com.example.banaoTaskGalleryApp.data.remote.response.ResponseModel
import com.example.banaoTaskGalleryApp.utils.constants.Constants.METHOD_RECENT
import com.example.banaoTaskGalleryApp.utils.constants.Constants.METHOD_SEARCH
import com.example.banaoTaskGalleryApp.utils.network.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class BasicRepository @Inject constructor(
    private val apiInterface: BasicApiInterface
) {
    suspend fun getPhotos(page: String,searchQuery:String?): NetworkResult<PhotosResponse> {
        return apiInterface.getPhotos(
            method = if (searchQuery.isNullOrBlank()){METHOD_RECENT} else{METHOD_SEARCH},
            page = page,
            searchQuery = searchQuery?:""
        )
    }
}