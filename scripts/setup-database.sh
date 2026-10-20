#!/usr/bin/env bash
# Creates the shopsphere database with tables and demo data (asks for the MySQL root password)
cd "$(dirname "$0")/.." || exit 1
mysql -u root -p < database/shopsphere.sql && echo "Database created."
