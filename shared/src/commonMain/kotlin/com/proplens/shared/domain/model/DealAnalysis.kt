package com.proplens.shared.domain.model

data class DealAnalysis(
    val pricePerSqft: Double,
    val grossYieldPercent: Double,
    val netYieldPercent: Double,
    val monthlyCashflowEstimate: Double,
    val verdict: Verdict
)
