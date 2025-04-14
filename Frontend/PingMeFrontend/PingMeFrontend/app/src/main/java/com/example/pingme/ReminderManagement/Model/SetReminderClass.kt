package com.example.pingme.ReminderManagement.Model

import java.util.Date

data class SetReminderClass(
    var title: String = "",
    var description: String = "",
    var date: Date = Date(),
    var priority: String = ""
)