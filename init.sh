#!/bin/bash

echo "🚀 Inizializzazione progetto WhatsApp AI Bot..."

# Crea struttura base
mkdir -p src/main/java/com/whatsbot/{controller,dto,service,repository,model,config}
mkdir -p src/main/resources
mkdir -p logs

# Copia i file di contesto
cp README.md ./README.md
cp AGENTS.md ./AGENTS.md
cp codex-config.json ./codex-config.json

# Crea file base Application
touch src/main/java/com/whatsbot/WhatsAppBotApplication.java

echo "✅ Struttura creata."
echo "📄 README.md, AGENTS.md e codex-config.json pronti."
