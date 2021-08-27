package com.nas.fireevacuation.activity.evacutation.model

data class EvacuationModel(
    val `data`: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)