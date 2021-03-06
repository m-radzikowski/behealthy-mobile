package com.sample.behealthy.models

import android.content.Context

data class User(var id: Int,
                var login: String,
                var password: String,
                var endoId: Int,
                var availableChests: Int,
                var gold: Int,
                var exp: Int,
                var lvl: Int) {
    companion object {
        private var instance: User? = null

        @Synchronized
        fun getInstance(ctx: Context): User {
            return instance!!
        }

        @Synchronized
        fun setInitialUser(usr: User) {
            instance = usr
        }
    }
}

