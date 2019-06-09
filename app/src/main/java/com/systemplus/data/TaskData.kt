package com.systemplus.data

data class TaskData(
        var document_id: String? = "",
        var name: String? = "",
        var weight: Double? = 0.0,
        var volume: Double? = 0.0,
        var startAddress: String? = "",
        var finishAddress: String? = "",
        var isDone: Boolean? = false
)