package com.example.banaoTaskGalleryApp.data.remote.api

import com.example.banaoTaskGalleryApp.data.remote.response.PhotosResponse
import com.example.banaoTaskGalleryApp.utils.network.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface BasicApiInterface {

    @GET("rest")
    suspend fun getPhotos(
        @Query("method") method:String,
        @Query("per_page") perPage:String = "10",
        @Query("page") page:String,
        @Query("api_key") apiKey:String = "f6af306c3ac2e3325e6f18c5339f60ec",
        @Query("format") format:String ="json",
        @Query("nojsoncallback") nojsoncallback:String ="1",
        @Query("extras") extras:String = "url_s",
        @Query("text") searchQuery:String = "",
    ): NetworkResult<PhotosResponse>
}