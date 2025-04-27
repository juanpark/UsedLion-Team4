#!/bin/bash

# Find all empty directories (excluding .git and node_modules) and add .gitkeep
find . -type d -empty \
  ! -path "./.git*" \
  ! -path "./node_modules*" \
  -exec touch {}/.gitkeep \;

echo ".gitkeep added to all empty directories."
