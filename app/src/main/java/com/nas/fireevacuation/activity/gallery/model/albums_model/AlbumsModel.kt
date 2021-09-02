package com.nas.fireevacuation.activity.gallery.model.albums_model

data class AlbumsModel(
    val `data`: Data,
    val message: String,
    val responsecode: Int,
    val validation_errors: List<Any>
)