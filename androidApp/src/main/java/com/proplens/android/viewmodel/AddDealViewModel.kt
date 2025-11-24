package com.proplens.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proplens.android.di.AppContainer
import com.proplens.shared.domain.model.AreaStats
import com.proplens.shared.domain.model.Assumptions
import com.proplens.shared.domain.model.Deal
import com.proplens.shared.domain.model.DealAnalysis
import com.proplens.shared.domain.usecase.AnalyzeDealUseCase
import com.proplens.shared.domain.usecase.GetAreaStatsUseCase
import com.proplens.shared.domain.usecase.GetAssumptionsUseCase
import com.proplens.shared.domain.usecase.SaveDealUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class AddDealViewModel(
    private val analyzeDealUseCase: AnalyzeDealUseCase,
    private val getAreaStatsUseCase: GetAreaStatsUseCase,
    private val getAssumptionsUseCase: GetAssumptionsUseCase,
    private val saveDealUseCase: SaveDealUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddDealState())
    val state: StateFlow<AddDealState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val areas = getAreaStatsUseCase()
            val assumptions = getAssumptionsUseCase()
            _state.value = _state.value.copy(areaStats = areas, assumptions = assumptions)
            updateAnalysis()
        }
    }

    fun onEvent(event: AddDealEvent) {
        when (event) {
            is AddDealEvent.TitleChanged -> _state.value = _state.value.copy(title = event.value)
            is AddDealEvent.AreaChanged -> _state.value = _state.value.copy(area = event.value)
            is AddDealEvent.BedroomsChanged -> _state.value = _state.value.copy(bedrooms = event.value)
            is AddDealEvent.SizeChanged -> _state.value = _state.value.copy(sizeSqft = event.value)
            is AddDealEvent.AskingPriceChanged -> _state.value = _state.value.copy(askingPrice = event.value)
            is AddDealEvent.ExpectedRentChanged -> _state.value = _state.value.copy(expectedRent = event.value)
            is AddDealEvent.ServiceChargeChanged -> _state.value = _state.value.copy(serviceCharge = event.value)
            is AddDealEvent.AdditionalCostsChanged -> _state.value = _state.value.copy(additionalCosts = event.value)
            AddDealEvent.Save -> save()
        }
        if (event != AddDealEvent.Save) {
            updateAnalysis()
        }
    }

    private fun save() {
        viewModelScope.launch {
            buildDeal()?.let { deal ->
                _state.value = _state.value.copy(isSaving = true)
                saveDealUseCase(deal)
                _state.value = _state.value.copy(isSaving = false, saveSuccess = true)
            }
        }
    }

    private fun updateAnalysis() {
        viewModelScope.launch {
            val deal = buildDeal() ?: return@launch
            val areaStat = _state.value.areaStats.firstOrNull {
                it.area.equals(_state.value.area, ignoreCase = true)
            }
            val assumptions = _state.value.assumptions ?: return@launch
            val analysis = analyzeDealUseCase.analyze(deal, areaStat, assumptions)
            _state.value = _state.value.copy(analysis = analysis)
        }
    }

    private fun buildDeal(): Deal? {
        val bedrooms = _state.value.bedrooms.toIntOrNull() ?: 0
        val size = _state.value.sizeSqft.toDoubleOrNull() ?: return null
        val price = _state.value.askingPrice.toDoubleOrNull() ?: return null
        val rent = _state.value.expectedRent.toDoubleOrNull() ?: return null
        val serviceCharge = _state.value.serviceCharge.toDoubleOrNull()
        val additionalCosts = _state.value.additionalCosts.toDoubleOrNull()

        return Deal(
            id = _state.value.dealId.ifEmpty { Random.nextInt(100000, 999999).toString() },
            title = _state.value.title.ifEmpty { "New Deal" },
            area = _state.value.area,
            bedrooms = bedrooms,
            sizeSqft = size,
            askingPrice = price,
            expectedRentPerYear = rent,
            serviceChargePerYear = serviceCharge,
            additionalCostsPerYear = additionalCosts,
            createdAtMillis = System.currentTimeMillis()
        )
    }

    companion object {
        fun provideFactory(appContainer: AppContainer): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AddDealViewModel(
                        analyzeDealUseCase = appContainer.analyzeDealUseCase,
                        getAreaStatsUseCase = appContainer.getAreaStatsUseCase,
                        getAssumptionsUseCase = appContainer.getAssumptionsUseCase,
                        saveDealUseCase = appContainer.saveDealUseCase
                    ) as T
                }
            }
    }
}

sealed class AddDealEvent {
    data class TitleChanged(val value: String) : AddDealEvent()
    data class AreaChanged(val value: String) : AddDealEvent()
    data class BedroomsChanged(val value: String) : AddDealEvent()
    data class SizeChanged(val value: String) : AddDealEvent()
    data class AskingPriceChanged(val value: String) : AddDealEvent()
    data class ExpectedRentChanged(val value: String) : AddDealEvent()
    data class ServiceChargeChanged(val value: String) : AddDealEvent()
    data class AdditionalCostsChanged(val value: String) : AddDealEvent()
    object Save : AddDealEvent()
}

data class AddDealState(
    val dealId: String = "",
    val title: String = "",
    val area: String = "",
    val bedrooms: String = "",
    val sizeSqft: String = "",
    val askingPrice: String = "",
    val expectedRent: String = "",
    val serviceCharge: String = "",
    val additionalCosts: String = "",
    val areaStats: List<AreaStats> = emptyList(),
    val assumptions: Assumptions? = null,
    val analysis: DealAnalysis? = null,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false
)
