package com.gotocon.cdworkshop.filters;

import com.gotocon.cdworkshop.logging.ServiceMDC;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class LoggingFilterTest {

    LoggingFilter filter = new LoggingFilter();

    @Test
    public void shouldSetSessionIdIfPresentInHeaderOnMDC() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader(ServiceMDC.SESSION_ID_KEY)).thenReturn("user-session-id");
        filter.doFilter(request, response, filterChain);

        assertThat(ServiceMDC.getSessionId(), is("user-session-id"));

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldNOTSetAnySessionIdIfNOTPresentInHeaderOnMDC() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        filter.doFilter(request, response, filterChain);

        assertNull(ServiceMDC.getSessionId());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldCreateAndSetRequestIdOnMDC() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        filter.doFilter(request, response, filterChain);

        assertNotNull(ServiceMDC.getRequestId());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldSetRequestIdIfPresentInHeaderOnMDC() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader(ServiceMDC.REQUEST_ID_KEY)).thenReturn("user-request-id");
        filter.doFilter(request, response, filterChain);

        assertNotNull(ServiceMDC.getRequestId());
        String requestId = ServiceMDC.getRequestId();
        assertThat(requestId, is("user-request-id"));

        verify(filterChain).doFilter(request, response);
    }
}
