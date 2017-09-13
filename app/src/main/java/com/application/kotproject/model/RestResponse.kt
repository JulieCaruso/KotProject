package com.application.kotproject.model

data class RestResponse<T>(var success: Boolean,
                           var data: T)