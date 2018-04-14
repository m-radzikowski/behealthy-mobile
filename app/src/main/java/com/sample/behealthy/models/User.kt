package com.sample.behealthy.models

import android.content.Context

data class User(var id: Int,
                var login: String,
                var password: String,
                var endoId: Int,
                var availableChests: Int,
                var gold: Int) {
    companion object {
        private var instance: User? = null

        @Synchronized
        fun getInstance(ctx: Context): User {
            if (User == null)
                throw NullPointerException()

            return instance!!
        }
    }
}

