package com.rh.daily.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rh.daily.R
import com.rh.daily.ui.theme.Accent
import com.rh.daily.ui.theme.Card
import com.rh.daily.ui.theme.Muted
import com.rh.daily.ui.theme.SecondaryCard
import com.rh.daily.ui.theme.White

@Composable
fun SettingsScreen(
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onNotificationsChanged: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("Pengaturan", color = White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(5.dp))
            Text("Atur pengalaman RH DAILY.", color = Muted, fontSize = 14.sp)
            Spacer(Modifier.height(18.dp))
        }
        item {
            SettingSwitchRow(Icons.Rounded.DarkMode, "Tema gelap", "Tampilan utama RH DAILY", darkMode, onDarkModeChanged)
        }
        item {
            SettingSwitchRow(Icons.Rounded.NotificationsNone, "Notifikasi", "Simpan preferensi pengingat aktivitas", notificationsEnabled, onNotificationsChanged)
        }
        item {
            SettingRow(Icons.Rounded.Info, "Tentang RH DAILY", "Versi 3.0 • Data tersimpan lokal di perangkat")
        }
        item {
            Spacer(Modifier.height(8.dp))
            Divider(color = Color(0xFF24282F))
            Spacer(Modifier.height(18.dp))
            Text("Atur hari. Jalanin pelan-pelan.", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
            Surface(color = Color.Black, shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(R.drawable.rh_daily_hero), contentDescription = "RH DAILY artwork", modifier = Modifier.fillMaxWidth().height(170.dp), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
            }
        }
    }
}

@Composable
private fun SettingSwitchRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Surface(color = Card, shape = RoundedCornerShape(19.dp), modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(17.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBox(icon)
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(3.dp))
                Text(subtitle, color = Muted, fontSize = 12.sp)
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
private fun SettingRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Surface(color = Card, shape = RoundedCornerShape(19.dp), modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(17.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBox(icon)
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(3.dp))
                Text(subtitle, color = Muted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun IconBox(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(color = SecondaryCard, shape = RoundedCornerShape(14.dp), modifier = Modifier.size(44.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(icon, contentDescription = null, tint = Accent)
        }
    }
}
