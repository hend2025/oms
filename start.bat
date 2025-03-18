@echo off

ping 127.0.0.1 -n 5 > nul

for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":8002"') do taskkill /pid %%m -t -f 

ping 127.0.0.1 -n 5 > nul

start /b javaw -jar -Xmx256m -Xms64m -Dfile.encoding=utf-8 -Dsun.jnu.encoding=UTF-8 target\xp-version-2.0.9-SNAPSHOT.jar > D:\home\logs\xp-ver.txt 2>&1 & 

pause