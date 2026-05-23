@echo off
:: Configuración de codificación para evitar problemas con caracteres especiales
chcp 65001 > nul

:: Definición de variables (Documentación y nombres en español)
set "NOMBRE_ZIP=POO07-315113758-OBREGONHERNANDEZRAMSESALEJANDRO.zip"
set "CARPETA_JUEGO=juego"
set "CARPETA_RECURSOS=resources"

echo ==================================================
echo   PROCESO DE AUTOMATIZACIÓN DE ENTREGA - POO
echo ==================================================
echo.

:: TAREA 1: Borrar el archivo ZIP existente si es que existe
if exist "%NOMBRE_ZIP%" (
    echo [INFO] Detectado archivo ZIP previo. Eliminando...
    del /f /q "%NOMBRE_ZIP%"
    
    :: Verificación de seguridad por si el archivo está bloqueado
    if exist "%NOMBRE_ZIP%" (
        echo [ERROR] No se pudo eliminar el archivo anterior. 
        echo Asegúrate de cerrar WinRAR o VS Code y vuelve a intentarlo.
        goto FINALIZAR
    )
    echo [OK] Archivo anterior eliminado con éxito.
) else (
    echo [INFO] No se encontraron entregas previas. Procediendo...
)

echo.

:: VALIDACIÓN: Verificar la existencia de las carpetas requeridas
if not exist "%CARPETA_JUEGO%" (
    echo [ERROR] La carpeta '%CARPETA_JUEGO%' no se encuentra en este directorio.
    goto ERROR_CARPETAS
)
if not exist "%CARPETA_RECURSOS%" (
    echo [ERROR] La carpeta '%CARPETA_RECURSOS%' no se encuentra en este directorio.
    goto ERROR_CARPETAS
)

:: TAREA 2: Empaquetar las carpetas en la raíz del ZIP
echo [INFO] Comprimiendo '%CARPETA_JUEGO%' y '%CARPETA_RECURSOS%'...
echo Por favor, espera un momento...

powershell -Command "Compress-Archive -Path '%CARPETA_JUEGO%', '%CARPETA_RECURSOS%' -DestinationPath '%NOMBRE_ZIP%' -Force"

:: VERIFICACIÓN FINAL
if exist "%NOMBRE_ZIP%" (
    echo.
    echo ==================================================
    echo   ¡EMPAQUETADO EXITOSO!
    echo   Archivo listo para subir al servidor:
    echo   %NOMBRE_ZIP%
    echo ==================================================
) else (
    echo.
    echo [ERROR] Ocurrió un fallo inesperado al intentar estructurar el ZIP.
)
goto FINALIZAR

:ERROR_CARPETAS
echo.
echo [ERROR] Ejecuta este script únicamente desde la carpeta raíz de tu proyecto
echo donde 'juego' y 'resources' coexistan como carpetas hermanas.

:FINALIZAR
echo.
pause