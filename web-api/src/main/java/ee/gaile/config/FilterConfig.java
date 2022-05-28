package ee.gaile.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Aleksei Gaile 28-May-22
 */
@Slf4j
@Component
public class FilterConfig extends RequestContextFilter {

    private static final String ADMIN_ID = "b26ab859-a676-4372-811c-da5c98e3a213";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain filterChain)
            throws IOException, ServletException {
        String userId = request.getHeader("userId");
        if (ADMIN_ID.equals(userId)) {
            log.info("admin");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
