package com.capstone.sifood.data.remote.response

import com.capstone.sifood.vo.StatusResponse

class ApiResponse<T>(val status :StatusResponse,val body:T,val message :String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(StatusResponse.SUCCESS, body, null)
    }
}