package org.example.models

data class ErrorResponse(val error: ErrorData)

data class ErrorData(val code: Int, val message: String)
