@echo off
setlocal enabledelayedexpansion
echo --------------------------------------------
echo Iniciando Prueba de Carga %1 (100 Peticiones)
echo --------------------------------------------
set /A OK=0
set /A FAIL=0
FOR /L %%i IN (1,1,100) DO (
    curl.exe -s -o NUL -w "%%{http_code}" http://localhost:8080/login > temp.txt
    set /p CODE=<temp.txt
    if "!CODE!"=="200" (
        set /A OK+=1
    ) else (
        set /A FAIL+=1
    )
)
echo Resultados Prueba %1:
echo - Peticiones Exitosas (HTTP 200): !OK!
echo - Peticiones Fallidas / Errores: !FAIL!
echo --------------------------------------------
del temp.txt
