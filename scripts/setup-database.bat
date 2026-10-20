@echo off
REM Creates the shopsphere database with tables and demo data
cd /d "%~dp0.."
mysql -u root -p < database\shopsphere.sql
if %errorlevel%==0 echo Database created.
