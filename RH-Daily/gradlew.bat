@echo off
setlocal
set "DIRNAME=%~dp0"
set "JAR=%DIRNAME%gradle\wrapper\gradle-wrapper.jar"
set "URL=https://raw.githubusercontent.com/gradle/gradle/v8.9.0/gradle/wrapper/gradle-wrapper.jar"

if exist "%JAR%" goto runwrapper

where curl >nul 2>nul
if %ERRORLEVEL% EQU 0 (
  curl -fL "%URL%" -o "%JAR%"
)

if exist "%JAR%" goto runwrapper

where gradle >nul 2>nul
if %ERRORLEVEL% EQU 0 (
  gradle %*
  exit /b %ERRORLEVEL%
)

echo Gradle wrapper JAR is missing and no system Gradle was found.
echo Open the project in Android Studio with internet access, or install Gradle 8.9.
exit /b 1

:runwrapper
if not "%JAVA_HOME%"=="" (
  "%JAVA_HOME%\bin\java.exe" -classpath "%JAR%" org.gradle.wrapper.GradleWrapperMain %*
) else (
  java -classpath "%JAR%" org.gradle.wrapper.GradleWrapperMain %*
)
endlocal
