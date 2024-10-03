package com.usic.usic.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor{
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession(false);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("/orientacion_vocacional");
            return false;
        }

        String rol = (String) session.getAttribute("nombre_rol");
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/vista-administrador") && !rol.equals("ADMINISTRADOR") && !rol.equals("SUPER USUARIO")) {
            response.sendRedirect("/orientacion_vocacional"); // Redirige al login si el rol no es ADMINISTRADOR
            return false;
        } else if (requestURI.startsWith("/tests") && !rol.equals("ESTUDIANTES")) {
            response.sendRedirect("/orientacion_vocacional"); // Redirige al login si el rol no es ESTUDIANTES
            return false;
        }

        return true;
    }
}
