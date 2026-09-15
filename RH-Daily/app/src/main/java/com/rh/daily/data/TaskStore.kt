package com.rh.daily.data

import android.content.Context
import com.rh.daily.model.DailyTask
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.util.Base64

class TaskStore(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(date: LocalDate): List<DailyTask> {
        val key = dateKey(date)
        val raw = prefs.getString(key, null)
        if (raw == null) {
            val migrated = migrateLegacyTasks(date)
            if (migrated != null) return migrated
            return defaultTasks()
        }
        if (raw.isBlank()) return emptyList()
        return decode(raw)
    }

    fun save(date: LocalDate, tasks: List<DailyTask>) {
        prefs.edit().putString(dateKey(date), encode(tasks)).apply()
    }

    private fun migrateLegacyTasks(date: LocalDate): List<DailyTask>? {
        val legacy = prefs.getString(LEGACY_KEY, null) ?: return null
        val tasks = legacy.split("\n")
            .filter(String::isNotBlank)
            .mapNotNull { row ->
                val parts = row.split("|", limit = 3)
                if (parts.size != 3) return@mapNotNull null
                DailyTask(
                    id = parts[0].toLongOrNull() ?: return@mapNotNull null,
                    title = parts[1].replace("\\n", "\n"),
                    done = parts[2] == "1",
                )
            }
        save(date, tasks)
        return tasks
    }

    private fun encode(tasks: List<DailyTask>): String = tasks.joinToString("\n") {
        val title = Base64.getEncoder().encodeToString(it.title.toByteArray(StandardCharsets.UTF_8))
        "${it.id}|${if (it.done) 1 else 0}|$title"
    }

    private fun decode(raw: String): List<DailyTask> = raw.split("\n")
        .filter(String::isNotBlank)
        .mapNotNull { row ->
            val parts = row.split("|", limit = 3)
            if (parts.size != 3) return@mapNotNull null
            val title = runCatching {
                String(Base64.getDecoder().decode(parts[2]), StandardCharsets.UTF_8)
            }.getOrNull() ?: return@mapNotNull null
            DailyTask(
                id = parts[0].toLongOrNull() ?: return@mapNotNull null,
                title = title,
                done = parts[1] == "1",
            )
        }

    private fun dateKey(date: LocalDate) = "tasks_${date}"

    private fun defaultTasks() = listOf(
        DailyTask(1L, "Bangun & rapihin tempat tidur"),
        DailyTask(2L, "Kerja / belajar fokus"),
        DailyTask(3L, "Olahraga ringan"),
        DailyTask(4L, "Review hari ini"),
        DailyTask(5L, "Siapkan rencana besok"),
    )

    companion object {
        private const val PREFS_NAME = "rh_daily_v3"
        private const val LEGACY_KEY = "tasks"
    }
}
