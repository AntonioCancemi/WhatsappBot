# WhatsApp AI Bot - Spring Boot MVP

## 📌 Obiettivo

Progetto backend in **Java 21 + Spring Boot 3.2+** per la creazione di un **bot WhatsApp intelligente**, con logica modulare, persistenza dati, classificazione intenti, e invio template via **WhatsApp Cloud API**.

Costruito per essere **scalabile**, **estendibile** e **vendibile**, ma abbastanza semplice da essere sviluppato anche da un solo sviluppatore.

## 🧱 Stack Tecnologico

- **Java 21**
- **Spring Boot 3.2+**
- **Maven**
- **PostgreSQL**
- **Lombok**
- **SLF4J** (log JSON)
- (Facoltativo) **React + Bootstrap** per dashboard

## 📐 Struttura dei pacchetti

```
src/main/java/com/whatsbot/
├── controller/         # API REST (webhook, template)
├── dto/                # DTO input/output
├── service/            # Business logic e NLP
├── repository/         # Spring Data JPA
├── model/              # Entità JPA
├── config/             # Configurazioni globali
└── WhatsAppBotApplication.java
```

## 🛠️ Setup iniziale

Dopo aver clonato il repository esegui lo script di scaffolding:

```bash
./init.sh
```

Il comando crea la struttura base `src/main/...` qualora non fosse presente.

## 🔑 Variabili d'ambiente richieste

Definisci queste variabili nel tuo ambiente (o in un file `.env`) prima di avviare i servizi:

- `WHATSAPP_BASE_URL` – URL base delle API WhatsApp Cloud
- `WHATSAPP_PHONE_NUMBER_ID` – ID del numero collegato all'account
- `WHATSAPP_ACCESS_TOKEN` – token di accesso alle API
- `WHATSAPP_APP_SECRET` – secret per validare il webhook
- `POSTGRES_DB` – nome del database
- `POSTGRES_USER` – utente del database
- `POSTGRES_PASSWORD` – password del database

## 🔁 Flusso principale

1. Utente scrive su WhatsApp
2. WhatsApp invia messaggio al tuo **WebhookController**
3. Il messaggio viene validato e loggato
4. Il servizio `MessageProcessorService` esegue:
   - Classificazione dell’intento (`IntentClassifier`)
   - Salvataggio messaggio
   - Invio template se previsto (`WhatsAppSenderService`)
5. Tutto è tracciato in DB e log strutturati

## 📬 Endpoint chiave

| Metodo | Endpoint            | Descrizione                        |
|--------|---------------------|------------------------------------|
| POST   | /webhook/receive    | Riceve messaggi da WhatsApp Cloud |
| POST   | /template/send      | Invia un template a un contatto   |
| GET    | /message/history    | (Opzionale) Visualizza i log      |

## 🧠 NLP / Intent (MVP)

Sistema leggero di classificazione intent tramite regex o chiamate a HuggingFace:

- BOOKING → prenotazione
- INFO → richieste orari/info
- CANCEL → annullamenti
- GENERIC → fallback

## 🗃️ Database Schema (base)

| Tabella  | Campi principali                                        |
|----------|---------------------------------------------------------|
| `users`  | id, phone, name, language, createdAt, updatedAt         |
| `messages` | id, user_id, text, direction, intent, timestamp       |

## 🔐 Sicurezza

- Header token per validazione Webhook
- Variabili sensibili fornite tramite `.env` o variabili d'ambiente
- Nessun dato sensibile nei log

## 🔑 Variabili d'Ambiente

| Nome variabile | Descrizione |
|---------------|-------------|
| `WHATSAPP_PHONE_NUMBER_ID` | ID del numero di telefono WhatsApp |
| `WHATSAPP_ACCESS_TOKEN` | Token di accesso API |
| `WHATSAPP_APP_SECRET` | Segreto per validare le firme dei webhook |
| `NGROK_AUTHTOKEN` | Token per Ngrok (opzionale) |

## 📦 Integrazione WhatsApp

- Utilizza **WhatsApp Cloud API**
- Invia solo **template approvati**
- Webhook ricevuto su endpoint pubblico (es: Ngrok, VPS)

## 🧰 Esecuzione locale (dev)

1. Clona il repo
2. Esegui `./init.sh` per generare la struttura iniziale
3. Definisci le variabili d'ambiente descritte sopra
4. Avvia i servizi con `docker-compose up --build`
5. Esponi `/webhook/receive` (opzionale con Ngrok)
6. Avvia lo stub di onboarding:
   - `curl -X POST http://localhost:8080/onboard/start`
   - `curl -X POST http://localhost:8080/onboard/verify`
7. Testa l'invio messaggi tramite API WhatsApp

## 📈 Estensioni future

- Multi-tenant support
- Dashboard React completa
- Intent con AI dinamico
- CRM integration
- Drag & drop conversation builder

## ✨ Best Practices

- Tutte le logiche business in `service/`
- DTO validati con `@Valid`
- Logging strutturato con SLF4J
- Nessuna logica hardcoded nei controller
- Repository solo con Spring Data
- Testabili facilmente con JUnit + Mockito
## 🚀 Avvio con Docker Compose

Esegui tutti i servizi tramite:

```bash
docker-compose up --build
```

Docker Compose leggerà le variabili definite nel tuo ambiente o nel file `.env`.


## 📣 Prompt per Codex / GPT

> *Questo progetto segue naming e struttura standard Java + Spring Boot.*
> *Quando completi classi o servizi, rispetta questi pacchetti: `controller`, `service`, `dto`, `repository`, `model`, `config`.*
