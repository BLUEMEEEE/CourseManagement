package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        chain.doFilter(req, httpServletResponse);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
