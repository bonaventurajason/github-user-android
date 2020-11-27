package com.bonaventurajason.githubuser.data.model

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)