package com.lyl.boon.net.entity

import java.util.*

data class FavoriteEntity(
        var id: String,
        var title: String,
        var author: String,
        var url: String,
        var createAt: Date
)