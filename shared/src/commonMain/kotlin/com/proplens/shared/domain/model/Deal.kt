package com.proplens.shared.domain.model

data class Deal(
    val id: String,
    val title: String,
    val area: String,
    val bedrooms: Int,
    val sizeSqft: Double,
    val askingPrice: Double,
    val expectedRentPerYear: Double,
    val serviceChargePerYear: Double? = null,
    val additionalCostsPerYear: Double? = null,
    val createdAtMillis: Long
)
