# WhatsappBot

Service for sending WhatsApp template messages using the WhatsApp Cloud API.

## Usage
1. Configure `src/main/resources/application.yml` with your `phone-number-id` and `access-token`.
2. Use `WhatsAppSenderService#sendTemplateMessage(phoneNumber, templateName, parameters)` to send approved template messages.

A sample request payload is provided in `src/main/resources/sample-template.json`.
