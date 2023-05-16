package com.example.banaoTaskGalleryApp.data.remote.response

import com.google.gson.annotations.SerializedName

data class PhotosResponse(

	@field:SerializedName("stat")
	val stat: String? = null,

	@field:SerializedName("photos")
	val photos: Photos? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

data class PhotoItem(

	@field:SerializedName("url_s")
	val urlS: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

)

data class Photos(

	@field:SerializedName("perpage")
	val perpage: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("photo")
	val photo: List<PhotoItem>? = null,

	@field:SerializedName("page")
	val page: Int? = null
)
