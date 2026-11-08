package com.mybudget.app.data.mapper

import com.mybudget.app.data.local.entity.CategoryEntity
import com.mybudget.app.domain.model.Category

fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    iconEmoji = iconEmoji,
    colorHex = colorHex
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    iconEmoji = iconEmoji,
    colorHex = colorHex
)
