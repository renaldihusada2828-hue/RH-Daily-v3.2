package com.rh.daily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EventNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.rh.daily.data.SettingsStore
import com.rh.daily.data.TaskStore
import com.rh.daily.ui.screens.ProgressScreen
import com.rh.daily.ui.screens.SettingsScreen
import com.rh.daily.ui.screens.TodayScreen
import com.rh.daily.ui.theme.Accent
import com.rh.daily.ui.theme.Bg
import com.rh.daily.ui.theme.Muted
import com.rh.daily.ui.theme.RHDailyTheme
import com.rh.daily.viewmodel.RHDailyState

private enum class Screen(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Today("Hari ini", Icons.Rounded.EventNote),
    Progress("Progress", Icons.Rounded.ShowChart),
    Settings("Pengaturan", Icons.Rounded.Settings),
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { RHDailyApp() }
    }
}

@Composable
private fun RHDailyApp() {
    val context = LocalContext.current
    val state = remember {
        RHDailyState(TaskStore(context.applicationContext), SettingsStore(context.applicationContext))
    }
    var selectedScreen by rememberSaveable { mutableIntStateOf(0) }

    RHDailyTheme(darkTheme = state.darkMode) {
        Scaffold(
            containerColor = Bg,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF101216),
                    tonalElevation = 0.dp,
                    modifier = Modifier.padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
                ) {
                    Screen.entries.forEachIndexed { index, screen ->
                        NavigationBarItem(
                            selected = selectedScreen == index,
                            onClick = { selectedScreen = index },
                            icon = { Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label, fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Accent,
                                selectedTextColor = Accent,
                                indicatorColor = Color(0xFF1D2418),
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted,
                            ),
                        )
                    }
                }
            },
        ) { innerPadding ->
            AnimatedContent(
                targetState = selectedScreen,
                transitionSpec = {
                    (fadeIn() + slideInVertically { it / 12 }) togetherWith
                        (fadeOut() + slideOutVertically { -it / 12 })
                },
                label = "screen_transition",
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            ) { screen ->
                when (screen) {
                    0 -> TodayScreen(
                        tasks = state.tasks,
                        onToggle = state::toggleTask,
                        onAdd = state::addTask,
                        onDelete = state::deleteTask,
                    )
                    1 -> ProgressScreen(state.tasks)
                    2 -> SettingsScreen(
                        darkMode = state.darkMode,
                        notificationsEnabled = state.notificationsEnabled,
                        onDarkModeChanged = state::setDarkMode,
                        onNotificationsChanged = state::setNotificationsEnabled,
                    )
                }
            }
        }
    }
}
