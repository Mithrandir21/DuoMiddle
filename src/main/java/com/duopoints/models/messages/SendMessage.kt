package com.duopoints.models.messages

import com.google.gson.annotations.SerializedName

data class SendMessage(
        val to: String? = null,
        @SerializedName("collapse_key")
        val collapseKey: String? = null,
        val ttl: Int? = null,
        val data: Map<String, Any>? = null
)