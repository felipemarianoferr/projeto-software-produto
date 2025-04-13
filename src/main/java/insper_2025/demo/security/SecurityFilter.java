package insper_2025.demo.security;

import insper_2025.demo.login.LoginService;
import insper_2025.demo.usuario.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        String token = request.getHeader("Authorization");

        Usuario usuario;
        try {
            usuario = loginService.validateToken(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Permite GET para todos os usuários autenticados
        if (HttpMethod.GET.matches(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Permite POST/PUT/DELETE apenas para ADMIN
        if ((HttpMethod.POST.matches(method) ||
                HttpMethod.PUT.matches(method) ||
                HttpMethod.DELETE.matches(method)) &&
                "ADMIN".equals(usuario.getPapel())) {

            filterChain.doFilter(request, response);
            return;
        }

        // Bloqueia qualquer outra tentativa
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
