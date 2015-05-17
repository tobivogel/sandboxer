package com.gotocon.cdworkshop.filters;

import com.gotocon.cdworkshop.logging.ServiceMDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        ServiceMDC.clear();

        String sessionId = httpRequest.getHeader(ServiceMDC.SESSION_ID_KEY);
        if (sessionId != null) {
            ServiceMDC.setSessionId(sessionId);
        }

        String requestId = getOrCreateRequestId(httpRequest);
        ServiceMDC.setRequestId(requestId);

        chain.doFilter(request, response);
    }

    private String getOrCreateRequestId(HttpServletRequest httpRequest) {
        String requestId = httpRequest.getHeader(ServiceMDC.REQUEST_ID_KEY);
        if (requestId != null) {
            return requestId;
        }

        return ("" + UUID.randomUUID().getMostSignificantBits()).replace("-","");
    }

    @Override
    public void destroy() {
    }
}
