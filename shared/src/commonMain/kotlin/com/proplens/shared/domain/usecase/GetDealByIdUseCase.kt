package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.model.Deal
import com.proplens.shared.domain.repository.DealRepository

class GetDealByIdUseCase(private val dealRepository: DealRepository) {
    suspend operator fun invoke(id: String): Deal? = dealRepository.getDealById(id)
}
