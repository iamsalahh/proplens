package com.proplens.shared.domain.model

data class Assumptions(
    val ltvPercent: Double = 75.0,
    val interestRatePercent: Double = 4.5,
    val termYears: Int = 25,
    val purchaseCostsPercent: Double = 4.0
)
