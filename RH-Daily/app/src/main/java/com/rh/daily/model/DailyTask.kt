package com.rh.daily.model

data class DailyTask(
    val id: Long,
    val title: String,
    val done: Boolean = false,
)
