# RH DAILY V3

**Atur hari. Jalanin pelan-pelan.**

Native Android productivity app built with Kotlin + Jetpack Compose + Material 3.

## What's in V3

- Existing RH DAILY UI and 3-tab navigation preserved.
- Today checklist with add, complete, delete, and animated progress.
- Tasks are stored locally and separated by date.
- Legacy V1 task data is migrated into today's V3 data on first launch.
- Progress screen remains simple and focused on today's completion.
- Settings now persist dark-mode and notification preferences.
- App artwork and launcher branding are retained.
- No Firebase, API keys, online database, or backend required.

## Requirements

- Android Studio with Android SDK 35
- JDK 17
- Internet access for the first Gradle sync/build

## Open in Android Studio

1. Extract this folder.
2. Open the **RH-Daily** folder.
3. Let Gradle sync finish.
4. Run the `app` configuration on a phone or emulator.

## Open in VS Code

Open the **RH-Daily** folder. Use the integrated terminal for builds.

### Build debug APK

```bash
./gradlew assembleDebug
```

Windows PowerShell:

```powershell
.\gradlew.bat assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions

Every push to `main` builds a debug APK and uploads it as the workflow artifact **RH-Daily-debug**.

You can also run the workflow manually from the Actions tab with **Run workflow**.

## Project layout

```text
RH-Daily/
├── .github/workflows/android.yml
├── app/
│   └── src/main/
│       ├── java/com/rh/daily/
│       │   ├── MainActivity.kt
│       │   ├── data/
│       │   ├── model/
│       │   ├── ui/
│       │   └── viewmodel/
│       └── res/
├── gradle/wrapper/
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew
```

## App identity

- Application ID: `com.rh.daily`
- Version name: `3.0`
- Version code: `3`
- Min SDK: 26
- Target/Compile SDK: 35
