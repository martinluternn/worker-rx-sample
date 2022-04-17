package com.example.data.api.base

import io.reactivex.functions.Function
import retrofit2.Response

class GetData<Model> : Function<Response<Model>, Model> {
    override fun apply(t: Response<Model>): Model = t.body() ?: throw RuntimeException()
}