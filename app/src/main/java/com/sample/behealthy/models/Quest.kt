package com.sample.behealthy.models

import java.util.*

data class Quest(var title: String, var description: String, var type: String, var date: Date, var value: Int, var done: Boolean)