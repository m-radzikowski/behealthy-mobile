package com.sample.behealthy.models

import java.util.*

data class Mission(var title: String, var description: String, var expDate: Date, var quests: Array<Quest>)