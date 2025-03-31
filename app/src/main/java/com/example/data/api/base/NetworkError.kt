package com.example.data.api.base

import java.io.IOException

open class BadRequestError(code: String) : IOException(code) {
    companion object {
        const val httpCode = 400
    }
}

class CommonError(message: String) : BadRequestError(message) {
    companion object {
        const val code = "CommonError"
    }
}

open class InternalServerError(errorMessage: String) : IOException(errorMessage) {
    companion object {
        const val httpCode = 500
    }
}

class NoInternetException : IOException() {
    override val message: String = "Internet connection problem, please check your connection!"
}