package com.duopoints

import com.duopoints.errorhandling.NullResultException

object Utils {
    fun <T> returnOrException(obj: T?): T = obj ?: throw NullResultException()
}