package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.model.AreaStats
import com.proplens.shared.domain.repository.AreaStatsRepository

class GetAreaStatsUseCase(private val areaStatsRepository: AreaStatsRepository) {
    suspend operator fun invoke(): List<AreaStats> = areaStatsRepository.getAreaStats()
    suspend fun forArea(area: String): AreaStats? = areaStatsRepository.getAreaStatsForArea(area)
}
