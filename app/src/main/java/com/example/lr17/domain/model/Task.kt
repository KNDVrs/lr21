package com.example.lr17.domain.model

data class Task(
    val id: Long,
    val title: String,
    val isCompleted: Boolean,
    val createdAt: Long
)