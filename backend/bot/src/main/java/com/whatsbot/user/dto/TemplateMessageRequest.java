package com.whatsbot.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateMessageRequest {
    @JsonProperty("messaging_product")
    private final String messagingProduct = "whatsapp";
    private String to;
    private final String type = "template";
    private Template template;

    public TemplateMessageRequest(String to, String templateName, List<String> parameters) {
        this.to = to;
        this.template = new Template(templateName, parameters);
    }

    public String getMessagingProduct() {
        return messagingProduct;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public Template getTemplate() {
        return template;
    }

    public static class Template {
        private String name;
        private Language language = new Language("it");
        private List<Component> components;

        public Template(String name, List<String> parameters) {
            this.name = name;
            this.components = List.of(new Component(parameters));
        }

        public String getName() {
            return name;
        }

        public Language getLanguage() {
            return language;
        }

        public List<Component> getComponents() {
            return components;
        }
    }

    public static class Language {
        private String code;

        public Language(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static class Component {
        private final String type = "body";
        private List<Parameter> parameters;

        public Component(List<String> values) {
            this.parameters = values.stream().map(Parameter::new).toList();
        }

        public String getType() {
            return type;
        }

        public List<Parameter> getParameters() {
            return parameters;
        }
    }

    public static class Parameter {
        private final String type = "text";
        private String text;

        public Parameter(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public String getText() {
            return text;
        }
    }
}
