package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.model.Deal
import com.proplens.shared.domain.repository.DealRepository

class SaveDealUseCase(private val dealRepository: DealRepository) {
    suspend operator fun invoke(deal: Deal) = dealRepository.saveDeal(deal)
}
