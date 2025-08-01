@echo off
echo Building and running SameSame Password Comparator...
echo.

REM Create output directory
if not exist "build\classes" mkdir build\classes

REM Download JavaFX if not present (simplified approach)
echo Note: This application requires JavaFX to run.
echo If you don't have JavaFX installed, please download it from:
echo https://openjfx.io/
echo.

REM Compile Java files
echo Compiling Java source files...
javac -d build\classes -cp "src\main\java" src\main\java\com\samesame\*.java src\main\java\com\samesame\controller\*.java src\main\java\com\samesame\service\*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed. Please check for errors.
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Copy resources
echo Copying resources...
if not exist "build\classes\fxml" mkdir build\classes\fxml
if not exist "build\classes\css" mkdir build\classes\css
copy "src\main\resources\fxml\*.fxml" "build\classes\fxml\"
copy "src\main\resources\css\*.css" "build\classes\css\"

echo.
echo To run the application, you need JavaFX runtime.
echo Please install JavaFX and run:
echo java --module-path "path\to\javafx\lib" --add-modules javafx.controls,javafx.fxml -cp build\classes com.samesame.PasswordComparatorApp
echo.
pause