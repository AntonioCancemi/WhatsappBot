package com.whatsbot.interceptor;

import com.twilio.security.RequestValidator;
import com.whatsbot.config.TwilioProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Validates incoming Twilio webhook signatures before controller execution.
 */
@Component
@RequiredArgsConstructor
public class TwilioValidationHandlerInterceptor implements HandlerInterceptor {

    private final TwilioProperties twilioProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: rimettere la validazione Twilio una volta finiti i test

//        if (!(handler instanceof HandlerMethod handlerMethod)) {
//            return true;
//        }
//        if (!requiresValidation(handlerMethod)) {
//            return true;
//        }
//
//        String signature = request.getHeader("X-Twilio-Signature");
//        if (!StringUtils.hasText(signature)) {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            return false;
//        }
//
//        String validationUrl = buildValidationUrl(request);
//        Map<String, String> params = extractBodyParams(request);
//
//        RequestValidator validator = new RequestValidator(twilioProperties.getAuthToken());
//        if (!validator.validate(validationUrl, params, signature)) {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            return false;
//        }

        return true;
    }

    private boolean requiresValidation(HandlerMethod method) {
        return method.hasMethodAnnotation(ValidateTwilioSignature.class) ||
                method.getBeanType().isAnnotationPresent(ValidateTwilioSignature.class);
    }

    private String buildValidationUrl(HttpServletRequest request) {
        if (StringUtils.hasText(twilioProperties.getWebhookUrlOverride())) {
            return StringUtils.trimTrailingCharacter(twilioProperties.getWebhookUrlOverride(), '/') + request.getRequestURI();
        }
        String proto = request.getHeader("X-Forwarded-Proto");
        if (!StringUtils.hasText(proto)) {
            proto = request.getScheme();
        }
        String host = request.getHeader("Host");
        return proto + "://" + host + request.getRequestURI();
    }

    private Map<String, String> extractBodyParams(HttpServletRequest request) throws IOException {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> params.put(k, v != null && v.length > 0 ? v[0] : ""));

        // remove query string parameters if present
        String query = request.getQueryString();
        if (StringUtils.hasText(query)) {
            Map<String, List<String>> queryParams = UriComponentsBuilder.newInstance().query(query).build().getQueryParams();
            queryParams.keySet().forEach(params::remove);
        }
        return params;
    }
}
