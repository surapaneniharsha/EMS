@echo off
title Employee Management System Project
echo Starting EMS...

cd /d %~dp0\..
java -cp lib/*;config/* com.infinira.ems.EmployeeTest
pause
