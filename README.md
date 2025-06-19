# WhatsappBot

Spring Boot service for sending WhatsApp template messages using the WhatsApp Cloud API.

## Usage
1. Configure `src/main/resources/application.yml` with your `phone-number-id` and `access-token`.
2. Build the project with `mvn package` and run it with `java -jar target/whatsappbot-1.0-SNAPSHOT.jar` or `mvn spring-boot:run`.
3. Use `WhatsAppSenderService#sendTemplateMessage(phoneNumber, templateName, parameters)` to send approved template messages.

A sample request payload is provided in `src/main/resources/sample-template.json`.
