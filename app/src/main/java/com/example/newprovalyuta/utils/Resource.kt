package com.example.newprovalyuta.utils

class Resource<out T>(var status: Status, val data: T?, var message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(Status.STATUS_SUCCESS, data, null)
        fun <T> error(message: String?, data: T?): Resource<T> =
            Resource(Status.STATUS_ERROR, data, message)
        fun <T> loading(data: T?): Resource<T> = Resource(Status.STATUS_LOADING, data, null)
    }
}