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
- Variabili sensibili in `application.yml`
- Nessun dato sensibile nei log

## 📦 Integrazione WhatsApp

- Utilizza **WhatsApp Cloud API**
- Invia solo **template approvati**
- Webhook ricevuto su endpoint pubblico (es: Ngrok, VPS)

## 🧰 Esecuzione locale (dev)

1. Clona il repo
2. Configura PostgreSQL e `application.yml`
3. Avvia con `mvn spring-boot:run`
4. Espone `/webhook/receive` (via Ngrok o NGINX)
5. Testa con Postman o direttamente con WhatsApp API

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

## 📣 Prompt per Codex / GPT

> *Questo progetto segue naming e struttura standard Java + Spring Boot.*
> *Quando completi classi o servizi, rispetta questi pacchetti: `controller`, `service`, `dto`, `repository`, `model`, `config`.*
