package com.d3if0028.katalogmov.model

data class MovieResponse(
        val total_pages: Int,
        val result: List<MovieModel>    
)