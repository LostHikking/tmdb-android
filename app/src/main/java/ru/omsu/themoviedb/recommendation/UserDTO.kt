package ru.omsu.themoviedb.recommendation

data class UserDTO(val userName: String,
                   val recommendations: List<RecommendationDTO>,
                   val scores: List<ScoreDTO>
)