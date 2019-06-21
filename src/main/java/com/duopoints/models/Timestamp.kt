package com.duopoints.models

import com.duopoints.RequestParameters
import com.fasterxml.jackson.annotation.JsonProperty

data class Timestamp(
        @JsonProperty(RequestParameters.TIMESTAMP_CREATED) val created_UTC: String? = null,
        @JsonProperty(RequestParameters.TIMESTAMP_LAST_MODIFIED) val last_modified_UTC: String? = null
)
