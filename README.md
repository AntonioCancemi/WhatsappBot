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
2. Copia `.env.example` in `.env` e imposta:
   - `WHATSAPP_PHONE_NUMBER_ID`
   - `WHATSAPP_ACCESS_TOKEN`
   - `WHATSAPP_APP_SECRET`
   - (facoltativo) `NGROK_AUTHTOKEN`
3. Avvia i servizi con `docker-compose up --build`
4. Esponi `/webhook/receive` (opzionale con Ngrok)
5. Avvia lo stub di onboarding:
   - `curl -X POST http://localhost:8080/onboard/start`
   - `curl -X POST http://localhost:8080/onboard/verify`
6. Testa l'invio messaggi tramite API WhatsApp

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


## 📣 Prompt per Codex / GPT

> *Questo progetto segue naming e struttura standard Java + Spring Boot.*
> *Quando completi classi o servizi, rispetta questi pacchetti: `controller`, `service`, `dto`, `repository`, `model`, `config`.*
