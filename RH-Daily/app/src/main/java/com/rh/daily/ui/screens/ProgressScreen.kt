package com.rh.daily.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rh.daily.model.DailyTask
import com.rh.daily.ui.theme.Accent
import com.rh.daily.ui.theme.Card
import com.rh.daily.ui.theme.Muted
import com.rh.daily.ui.theme.SecondaryCard
import com.rh.daily.ui.theme.White

@Composable
fun ProgressScreen(tasks: List<DailyTask>) {
    val done = tasks.count(DailyTask::done)
    val total = tasks.size
    val percent = if (total == 0) 0 else done * 100 / total
    val animated by animateFloatAsState(if (total == 0) 0f else done.toFloat() / total, animationSpec = spring(stiffness = 500f), label = "progress_screen")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Text("Progress", color = White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(5.dp))
            Text("Lihat seberapa konsisten lu hari ini.", color = Muted, fontSize = 14.sp)
        }
        item {
            Surface(color = Card, shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(22.dp)) {
                    Row {
                        Text("$percent%", color = Accent, fontSize = 48.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.width(8.dp))
                        Text("hari ini", color = Muted, fontSize = 14.sp, modifier = Modifier.padding(top = 29.dp))
                    }
                    Spacer(Modifier.height(18.dp))
                    Box(Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(50)).background(SecondaryCard)) {
                        Box(Modifier.fillMaxWidth(animated).height(10.dp).clip(RoundedCornerShape(50)).background(Accent))
                    }
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                StatCard("$done", "aktivitas selesai", Modifier.weight(1f))
                StatCard("$total", "total aktivitas", Modifier.weight(1f))
            }
        }
        item {
            Surface(color = SecondaryCard, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp)) {
                    Text(if (percent >= 100) "Nice. Hari ini kelar." else "Keep going.", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("Progress kecil tetap dihitung.", color = Muted, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun StatCard(number: String, label: String, modifier: Modifier) {
    Surface(color = Card, shape = RoundedCornerShape(20.dp), modifier = modifier) {
        Column(Modifier.padding(18.dp)) {
            Text(number, color = White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(4.dp))
            Text(label, color = Muted, fontSize = 12.sp)
        }
    }
}
