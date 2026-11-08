@echo off
setlocal
set GRADLE_VERSION=8.14.5
if "%GRADLE_USER_HOME%"=="" set GRADLE_USER_HOME=%USERPROFILE%\.gradle
set DIST_DIR=%GRADLE_USER_HOME%\wrapper\dists\gradle-%GRADLE_VERSION%-bin\mybudget
set GRADLE_HOME=%DIST_DIR%\gradle-%GRADLE_VERSION%
set GRADLE_EXE=%GRADLE_HOME%\bin\gradle.bat
if not exist "%GRADLE_EXE%" (
  if not exist "%DIST_DIR%" mkdir "%DIST_DIR%"
  echo Downloading Gradle %GRADLE_VERSION%...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $u='https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip'; $z=Join-Path '%DIST_DIR%' 'gradle.zip'; Invoke-WebRequest -Uri $u -OutFile $z; Expand-Archive -Path $z -DestinationPath '%DIST_DIR%' -Force; Remove-Item $z"
  if errorlevel 1 exit /b 1
)
call "%GRADLE_EXE%" %*
exit /b %ERRORLEVEL%
