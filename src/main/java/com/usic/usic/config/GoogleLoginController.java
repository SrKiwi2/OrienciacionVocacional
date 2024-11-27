package com.usic.usic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class GoogleLoginController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/oauth2/success")
    public String handleOAuth2Success(Authentication authentication, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, RedirectAttributes flash) {
        // Obtener la información del correo desde Google
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        System.out.println(email);

        // Buscar el usuario en la base de datos a través del correo
        String estado = usuarioService.findEstadoByCorreoOrDefault(email);

        Usuario usuario = usuarioService.findByCorreo(email).orElse(null);

        System.out.println(usuario);

        if (usuario.getRol().getNombre().equals("ESTUDIANTES")) {
            // Si el estado es 'E', redirigir a la vista de tests
                HttpSession sessionEstudiantes = request.getSession(true);
                sessionEstudiantes.setAttribute("usuario", usuario);
                sessionEstudiantes.setAttribute("persona", usuario.getPersona());
                sessionEstudiantes.setAttribute("nombre_rol", usuario.getRol().getNombre());
                flash.addAttribute("success", usuario.getPersona().getNombre());
            return "redirect:/tests";
        } else {
            if ("NO EXISTE".equals(estado)) {
                // Si no existe el usuario, mostrar mensaje de registro
                System.out.println("Debe registarse en el pre test o inscribirse");
                redirectAttributes.addFlashAttribute("message", "Debes registrarte para acceder a los recursos.");
                return "redirect:/orientacion_vocacional?register=true";

            } else {
                // Si el estado es diferente a 'E', mostrar mensaje de inhabilitación
                System.out.println("Debes inscribirte o  aun no estas habilitado");
                redirectAttributes.addFlashAttribute("message", "Debes estar habilitado para acceder a los recursos.");
                return "redirect:/orientacion_vocacional?not-authorized=true";
            }
        }
    }
}
