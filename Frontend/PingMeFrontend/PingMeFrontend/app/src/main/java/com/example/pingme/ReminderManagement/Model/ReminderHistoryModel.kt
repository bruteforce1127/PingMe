package com.example.pingme.ReminderManagement.Model

import java.util.Date

data class ReminderHistory(
    val id: Long,
    val title: String,
    val description: String,
    val date: Date,
    val priority: String
)

data class ReminderHistoryList(
    val username: String,
    val remindersList: List<ReminderHistory>
)