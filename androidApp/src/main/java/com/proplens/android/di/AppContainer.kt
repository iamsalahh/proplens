package com.proplens.android.di

import com.proplens.shared.data.repository.AreaStatsRepositoryImpl
import com.proplens.shared.data.repository.AssumptionsRepositoryImpl
import com.proplens.shared.data.repository.DealRepositoryImpl
import com.proplens.shared.domain.usecase.AnalyzeDealUseCase
import com.proplens.shared.domain.usecase.DeleteDealUseCase
import com.proplens.shared.domain.usecase.GetAreaStatsUseCase
import com.proplens.shared.domain.usecase.GetAssumptionsUseCase
import com.proplens.shared.domain.usecase.GetDealByIdUseCase
import com.proplens.shared.domain.usecase.GetDealsUseCase
import com.proplens.shared.domain.usecase.SaveAssumptionsUseCase
import com.proplens.shared.domain.usecase.SaveDealUseCase

class AppContainer {
    private val dealRepository = DealRepositoryImpl()
    private val areaStatsRepository = AreaStatsRepositoryImpl()
    private val assumptionsRepository = AssumptionsRepositoryImpl()

    val analyzeDealUseCase = AnalyzeDealUseCase()
    val getDealsUseCase = GetDealsUseCase(dealRepository)
    val getDealByIdUseCase = GetDealByIdUseCase(dealRepository)
    val saveDealUseCase = SaveDealUseCase(dealRepository)
    val deleteDealUseCase = DeleteDealUseCase(dealRepository)
    val getAreaStatsUseCase = GetAreaStatsUseCase(areaStatsRepository)
    val getAssumptionsUseCase = GetAssumptionsUseCase(assumptionsRepository)
    val saveAssumptionsUseCase = SaveAssumptionsUseCase(assumptionsRepository)
}
