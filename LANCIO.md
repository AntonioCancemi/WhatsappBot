# Guida al Lancio del Progetto

Questa guida descrive i passaggi per avviare localmente il bot WhatsApp e la dashboard frontend.

## Requisiti

- **Java 21** e **Maven 3.9+**
- **Node.js 18+** (per il frontend)
- **PostgreSQL** attivo e raggiungibile

## Configurazione

1. Clonare il repository:
   ```bash
   git clone <repo-url>
   cd WhatsappBot
   ```
2. Creare il database e configurare il file `.env` copiando `.env.example` e inserendo le credenziali WhatsApp.
3. (Opzionale) Configurare il proxy pubblico per il webhook, ad esempio con Ngrok.

## Avvio Backend

Nella cartella `bot` eseguire:
```bash
mvn spring-boot:run
```
Il servizio espone le API su `http://localhost:8080`.

## Avvio Frontend

Nella cartella `frontend` eseguire:
```bash
npm install
npm run dev
```
La dashboard sarà disponibile su `http://localhost:3000` e invierà le richieste al backend.

## Test

È possibile testare l'endpoint `/webhook/receive` tramite Postman o collegando la WhatsApp Cloud API al proxy pubblico configurato.
