package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.model.AreaStats
import com.proplens.shared.domain.model.Assumptions
import com.proplens.shared.domain.model.Deal
import com.proplens.shared.domain.model.DealAnalysis
import com.proplens.shared.domain.model.Verdict

class AnalyzeDealUseCase {
    fun analyze(
        deal: Deal,
        areaStats: AreaStats?,
        assumptions: Assumptions
    ): DealAnalysis {
        val pricePerSqft = if (deal.sizeSqft > 0) deal.askingPrice / deal.sizeSqft else 0.0
        val grossYieldPercent = if (deal.askingPrice > 0) {
            (deal.expectedRentPerYear / deal.askingPrice) * 100
        } else {
            0.0
        }

        val totalAnnualCosts = (deal.serviceChargePerYear ?: 0.0) + (deal.additionalCostsPerYear ?: 0.0)
        val netRentPerYear = deal.expectedRentPerYear - totalAnnualCosts
        val netYieldPercent = if (deal.askingPrice > 0) (netRentPerYear / deal.askingPrice) * 100 else 0.0

        val loanAmount = deal.askingPrice * (assumptions.ltvPercent / 100)
        val yearlyInterest = loanAmount * (assumptions.interestRatePercent / 100)
        val monthlyInterest = yearlyInterest / 12
        val monthlyNetRent = netRentPerYear / 12
        val monthlyCashflowEstimate = monthlyNetRent - monthlyInterest

        val verdict = areaStats?.let {
            val ratio = if (it.avgPricePerSqft > 0) pricePerSqft / it.avgPricePerSqft else 1.0
            when {
                ratio < 0.9 -> Verdict.UNDERVALUED
                ratio <= 1.1 -> Verdict.FAIR
                else -> Verdict.OVERPRICED
            }
        } ?: Verdict.FAIR

        return DealAnalysis(
            pricePerSqft = pricePerSqft,
            grossYieldPercent = grossYieldPercent,
            netYieldPercent = netYieldPercent,
            monthlyCashflowEstimate = monthlyCashflowEstimate,
            verdict = verdict
        )
    }
}
