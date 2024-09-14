package com.awesome.manager.core.network.model.request

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName


@Resource("rest/v1/currencies")
data object Currency {

    @Resource("")
    data class Get(
        @SerialName("select") val select: String = "*",
        @SerialName("updated_at") val updatedAt: String,
        val parent: Currency = Currency
    )

}