package com.example.lr17.data.remote.mapper

import com.example.lr17.data.remote.dto.TaskDto
import com.example.lr17.domain.model.Task

fun TaskDto.toDomain(): Task = Task(
    id = id,
    title = title,
    isCompleted = isCompleted,
    createdAt = createdAt
)

fun Task.toDto(): TaskDto = TaskDto(
    id = id,
    title = title,
    isCompleted = isCompleted,
    createdAt = createdAt
)