package com.nas.fireevacuation.activity.gallery.model.videos_model

data class VideosModel(
    val `data`: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)