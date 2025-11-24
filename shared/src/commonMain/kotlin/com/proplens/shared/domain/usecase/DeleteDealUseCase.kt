package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.repository.DealRepository

class DeleteDealUseCase(private val dealRepository: DealRepository) {
    suspend operator fun invoke(id: String) = dealRepository.deleteDeal(id)
}
