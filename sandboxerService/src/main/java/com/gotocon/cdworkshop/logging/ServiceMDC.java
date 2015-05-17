package com.gotocon.cdworkshop.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.MDC;

public class ServiceMDC {

    public static final String SESSION_ID_KEY = "session_id";
    public static final String REQUEST_ID_KEY = "request_id";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setSessionId(String sessionId) {
        MDC.put(SESSION_ID_KEY, sessionId);
    }

    public static String getSessionId() {
        return MDC.get(SESSION_ID_KEY);
    }

    public static void setRequestId(String requestId) {
        MDC.put(REQUEST_ID_KEY, requestId);
    }

    public static String getRequestId() {
        return MDC.get(REQUEST_ID_KEY);
    }

    public static void clear() {
        MDC.clear();
    }
}
