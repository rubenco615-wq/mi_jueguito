@echo off
echo Compilando BingoSwingJunior...
if not exist out mkdir out
javac -d out -sourcepath src src\com\bingo\*.java
if %ERRORLEVEL% == 0 (
    echo Compilacion exitosa! Ejecutando...
    java -cp out com.bingo.Main
) else (
    echo Error al compilar. Revisa los mensajes de arriba.
    pause
)
