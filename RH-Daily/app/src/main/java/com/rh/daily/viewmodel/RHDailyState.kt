package com.rh.daily.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rh.daily.data.SettingsStore
import com.rh.daily.data.TaskStore
import com.rh.daily.model.DailyTask
import java.time.LocalDate

class RHDailyState(
    private val taskStore: TaskStore,
    private val settingsStore: SettingsStore,
) {
    val today: LocalDate = LocalDate.now()

    var tasks by mutableStateOf(taskStore.load(today))
        private set

    var darkMode by mutableStateOf(settingsStore.isDarkMode())
        private set

    var notificationsEnabled by mutableStateOf(settingsStore.notificationsEnabled())
        private set

    fun toggleTask(id: Long) {
        tasks = tasks.map { if (it.id == id) it.copy(done = !it.done) else it }
        persist()
    }

    fun addTask(title: String) {
        val clean = title.trim()
        if (clean.isEmpty()) return
        val id = (tasks.maxOfOrNull(DailyTask::id) ?: 0L) + 1L
        tasks = tasks + DailyTask(id, clean)
        persist()
    }

    fun deleteTask(id: Long) {
        tasks = tasks.filterNot { it.id == id }
        persist()
    }

    fun setDarkMode(value: Boolean) {
        darkMode = value
        settingsStore.setDarkMode(value)
    }

    fun setNotificationsEnabled(value: Boolean) {
        notificationsEnabled = value
        settingsStore.setNotificationsEnabled(value)
    }

    fun completionFor(date: LocalDate): Float {
        val items = taskStore.load(date)
        if (items.isEmpty()) return 0f
        return items.count(DailyTask::done).toFloat() / items.size
    }

    fun tasksFor(date: LocalDate): List<DailyTask> = taskStore.load(date)

    private fun persist() = taskStore.save(today, tasks)
}
