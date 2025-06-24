package com.whatsbot.user.intent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Simple classifier mapping common Italian phrases to intents.
 */
public class IntentClassifier {

    private final Map<Pattern, IntentType> patternMap = new LinkedHashMap<>();

    public IntentClassifier() {
        patternMap.put(Pattern.compile("(?i)\\b(voglio\\s+prenotare|prenotare|vorrei\\s+prenotare)\\b"), IntentType.BOOKING);
        patternMap.put(Pattern.compile("(?i)\\b(annulla\\s+visita|cancella\\s+prenotazione|annullare\\s+prenotazione)\\b"), IntentType.CANCEL);
        patternMap.put(Pattern.compile("(?i)\\b(orari\\s+di\\s+apertura|quando\\s+siete\\s+aperti|orario\\s+di\\s+apertura)\\b"), IntentType.INFO);
    }

    /**
     * Classify the incoming text into an intent.
     *
     * @param text user input
     * @return detected intent or GENERIC if no pattern matches
     */
    public IntentType classify(String text) {
        if (text == null || text.isBlank()) {
            return IntentType.GENERIC;
        }
        for (Map.Entry<Pattern, IntentType> entry : patternMap.entrySet()) {
            if (entry.getKey().matcher(text).find()) {
                return entry.getValue();
            }
        }
        return IntentType.GENERIC;
    }

    /**
     * Allows to register additional patterns dynamically.
     *
     * @param regex regex pattern to match
     * @param intent intent associated with the pattern
     */
    public void addPattern(String regex, IntentType intent) {
        patternMap.put(Pattern.compile(regex, Pattern.CASE_INSENSITIVE), intent);
    }
}
