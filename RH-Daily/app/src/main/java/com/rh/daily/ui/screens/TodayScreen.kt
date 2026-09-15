package com.rh.daily.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rh.daily.model.DailyTask
import com.rh.daily.ui.theme.Accent
import com.rh.daily.ui.theme.Card
import com.rh.daily.ui.theme.Muted
import com.rh.daily.ui.theme.SecondaryCard
import com.rh.daily.ui.theme.White

@Composable
fun TodayScreen(
    tasks: List<DailyTask>,
    onToggle: (Long) -> Unit,
    onAdd: (String) -> Unit,
    onDelete: (Long) -> Unit,
) {
    var showInput by rememberSaveable { mutableStateOf(false) }
    var input by rememberSaveable { mutableStateOf("") }
    val done = tasks.count(DailyTask::done)
    val progress = if (tasks.isEmpty()) 0f else done.toFloat() / tasks.size
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(stiffness = 500f),
        label = "today_progress",
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Column {
                Text("RH DAILY", color = White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(4.dp))
                Text("Atur hari. Jalanin pelan-pelan.", color = Muted, fontSize = 14.sp)
                Spacer(Modifier.height(30.dp))
                Text("Selamat datang, Rey.", color = White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(5.dp))
                Text("Nggak harus sempurna. Yang penting jalan.", color = Muted, fontSize = 14.sp)
            }
        }
        item { ProgressCard(done, tasks.size, progress, animatedProgress) }
        item {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("AKTIVITAS HARI INI", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp, modifier = Modifier.weight(1f))
                Text("$done / ${tasks.size}", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
        items(tasks, key = DailyTask::id) { task ->
            TaskRow(task, onToggle = { onToggle(task.id) }, onDelete = { onDelete(task.id) })
        }
        item {
            AnimatedVisibility(
                visible = showInput,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut() + slideOutVertically { it / 2 },
            ) {
                AddTaskInput(
                    value = input,
                    onValueChange = { input = it },
                    onCancel = { input = ""; showInput = false },
                    onAdd = {
                        if (input.isNotBlank()) {
                            onAdd(input)
                            input = ""
                            showInput = false
                        }
                    },
                )
            }
        }
        item { if (!showInput) AddActivityButton { showInput = true } }
    }
}

@Composable
private fun ProgressCard(done: Int, total: Int, progress: Float, animatedProgress: Float) {
    val message = when {
        progress >= 1f && total > 0 -> "Mantap. Hari ini beres."
        progress >= 0.5f -> "Udah lebih dari setengah. Lanjut pelan."
        else -> "Satu langkah dulu."
    }
    Surface(shape = RoundedCornerShape(24.dp), color = Card, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text("$done", color = White, fontSize = 42.sp, fontWeight = FontWeight.ExtraBold)
                Text(" / $total selesai", color = Muted, fontSize = 14.sp, modifier = Modifier.padding(bottom = 7.dp))
            }
            Spacer(Modifier.height(16.dp))
            ProgressBar(animatedProgress)
            Spacer(Modifier.height(13.dp))
            Text(message, color = Muted, fontSize = 13.sp)
        }
    }
}

@Composable
private fun ProgressBar(progress: Float) {
    Box(Modifier.fillMaxWidth().height(9.dp).clip(RoundedCornerShape(50)).background(SecondaryCard)) {
        Box(Modifier.fillMaxWidth(progress).height(9.dp).clip(RoundedCornerShape(50)).background(Accent))
    }
}

@Composable
private fun TaskRow(task: DailyTask, onToggle: () -> Unit, onDelete: () -> Unit) {
    val scale by animateFloatAsState(
        targetValue = if (task.done) 1.04f else 1f,
        animationSpec = spring(stiffness = 700f),
        label = "task_scale",
    )
    Surface(shape = RoundedCornerShape(18.dp), color = if (task.done) Color(0xFF12151A) else Card, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onToggle).padding(start = 8.dp, top = 7.dp, bottom = 7.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier.size(48.dp).scale(scale).clip(CircleShape).background(if (task.done) Accent else SecondaryCard).clickable(onClick = onToggle),
                contentAlignment = Alignment.Center,
            ) {
                if (task.done) Icon(Icons.Rounded.Check, contentDescription = "Selesai", tint = Color(0xFF172000), modifier = Modifier.size(22.dp))
                else Box(Modifier.size(16.dp).clip(CircleShape).background(Color(0xFF343A43)))
            }
            Spacer(Modifier.width(13.dp))
            Text(task.title, color = if (task.done) Muted else White, fontSize = 15.sp, fontWeight = FontWeight.Medium, textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None, modifier = Modifier.weight(1f))
            IconButton(onClick = onDelete) { Icon(Icons.Rounded.DeleteOutline, contentDescription = "Hapus aktivitas", tint = Color(0xFF626973)) }
        }
    }
}

@Composable
private fun AddActivityButton(onClick: () -> Unit) {
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color(0xFF172000)), shape = RoundedCornerShape(17.dp), modifier = Modifier.fillMaxWidth().height(54.dp)) {
        Icon(Icons.Rounded.Add, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text("Tambah aktivitas", fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AddTaskInput(value: String, onValueChange: (String) -> Unit, onCancel: () -> Unit, onAdd: () -> Unit) {
    Surface(shape = RoundedCornerShape(20.dp), color = Card, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            OutlinedTextField(value = value, onValueChange = onValueChange, singleLine = true, placeholder = { Text("Tulis aktivitas baru...", color = Muted) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onCancel) { Text("Batal", color = Muted) }
                TextButton(onClick = onAdd) { Text("Add", color = Accent, fontWeight = FontWeight.Bold) }
            }
        }
    }
}
