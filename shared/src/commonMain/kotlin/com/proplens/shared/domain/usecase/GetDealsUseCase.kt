package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.model.Deal
import com.proplens.shared.domain.repository.DealRepository

class GetDealsUseCase(private val dealRepository: DealRepository) {
    suspend operator fun invoke(): List<Deal> = dealRepository.getDeals()
}
