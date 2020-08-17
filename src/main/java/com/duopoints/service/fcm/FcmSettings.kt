package com.duopoints.service.fcm

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "fcm")
@Component
class FcmSettings(var apiKey: String? = null)