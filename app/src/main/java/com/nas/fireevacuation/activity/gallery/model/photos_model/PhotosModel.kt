package com.nas.fireevacuation.activity.gallery.model.photos_model

data class PhotosModel(
    val `data`: Data,
    val message: String,
    val responsecode: Int,
    val validation_errors: List<Any>
)