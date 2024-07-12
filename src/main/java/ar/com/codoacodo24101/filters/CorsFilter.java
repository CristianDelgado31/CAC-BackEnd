package ar.com.codoacodo24101.filters;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/*" })
public class CorsFilter implements Filter {
    private List<String> origins = List.of("http://localhost:5500", "http://127.0.0.1:5500");
    private static final Logger LOGGER = Logger.getLogger(CorsFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String origin = httpRequest.getHeader("Origin");
        LOGGER.info("CORS Filter: Request from origin: " + origin);
        
        if (origin != null && origins.contains(origin)) {
            httpResponse.addHeader("Access-Control-Allow-Origin", origin);
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
            httpResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            
            // Manejo de preflight requests
            if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        } else {
            // Responder con un error 403 si el origen no está permitido
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "CORS origin not allowed");
            return;
        }

        chain.doFilter(request, response);
    }

}