@echo off
echo ========================================
echo    SameSame JavaFX GUI Application
echo ========================================
echo.

:: Set JavaFX path to user's installation
set JAVAFX_PATH=C:\Program Files\javafx-sdk-24.0.2\lib

:: Check if JavaFX exists
if not exist "%JAVAFX_PATH%" (
    echo ERROR: JavaFX not found at %JAVAFX_PATH%
    echo Please verify JavaFX installation path.
    pause
    exit /b 1
)

echo Compiling SameSame JavaFX Application...

:: Create build directory
if not exist "build\classes" mkdir "build\classes"

:: Compile Java files
javac -d build\classes --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "src\main\java" src\main\java\com\samesame\*.java src\main\java\com\samesame\controller\*.java src\main\java\com\samesame\service\*.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Compilation failed!
    echo Please check for syntax errors in the Java files.
    pause
    exit /b 1
)

echo Copying resources...
:: Copy FXML and CSS resources
xcopy "src\main\resources" "build\classes" /E /I /Y >nul 2>&1

echo.
echo Starting SameSame GUI Application...
echo.

:: Run the JavaFX application
:: Note: Warnings about deprecated methods are from JavaFX internals and can be safely ignored
java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -cp build\classes com.samesame.PasswordComparatorApp

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Application failed to start!
    echo Check the error messages above for details.
)

echo.
echo Application closed.
pause