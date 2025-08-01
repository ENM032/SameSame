@echo off
echo SameSame Password Comparator - Demo
echo ====================================
echo.
echo Compiling Java files...
if not exist "build\classes" mkdir build\classes

javac -d build\classes -cp "src\main\java" src\main\java\com\samesame\service\PasswordComparator.java src\main\java\com\samesame\DemoRunner.java src\main\java\com\samesame\ConsolePasswordComparator.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Running demo...
echo.
java -cp build\classes com.samesame.DemoRunner
echo.
echo Demo completed!
echo.
echo To run the interactive console version:
echo java -cp build\classes com.samesame.ConsolePasswordComparator
echo.
pause