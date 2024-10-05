package com.usic.usic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.usic.usic.model.Service.IUsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class GoogleLoginController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/oauth2/success")
    public String handleOAuth2Success(Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        // Obtener la información del correo desde Google
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");

        // Buscar el usuario en la base de datos a través del correo
        String estado = usuarioService.findEstadoByCorreoOrDefault(email);

        if ("E".equals(estado)) {
            // Si el estado es 'E', redirigir a la vista de tests
            System.out.println("Estuden con gmail ingreso a los test");
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
