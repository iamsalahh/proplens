package com.proplens.shared.domain.repository

import com.proplens.shared.domain.model.AreaStats

interface AreaStatsRepository {
    suspend fun getAreaStats(): List<AreaStats>
    suspend fun getAreaStatsForArea(area: String): AreaStats?
}
