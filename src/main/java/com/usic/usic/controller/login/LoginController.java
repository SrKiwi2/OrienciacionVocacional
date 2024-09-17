package com.usic.usic.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private IUsuarioService  usuarioService;

    @GetMapping(value = "/form-login")
    public String formLogin(Persona persona) {

        return "login/login.html";
    }

    @PostMapping("/iniciar-sesion")
    public String iniciarSesion(@RequestParam(value = "usuario") String user,
                            @RequestParam(value = "contrasena") String contrasena, 
                            Model model, HttpServletRequest request,
                            RedirectAttributes flash) {

        Usuario usuario = usuarioService.getUsuarioPassword(user, contrasena);

        if (usuario != null) {
            if (usuario.getEstado().equals("INHABILITADO")) {
                System.out.println("NO ESTA ACTIVO ESTE USUARIO");
                return "redirect:/vista-test";
                
            }

            if (usuario.getRol().getNombre().equals("ESTUDIANTES")) {
                flash.addAttribute("success", usuario.getPersona().getNombre());
                System.out.println("El usuario " + usuario.getPersona().getNombre() + " ha iniciado sesión como estudiante.");
                return "redirect:/tipo_test";  // Redirige a la vista para estudiantes
            }
        
            HttpSession sessionAdministrador = request.getSession(true);
            sessionAdministrador.setAttribute("usuario", usuario);
            sessionAdministrador.setAttribute("persona", usuario.getPersona());
            sessionAdministrador.setAttribute("nombre_rol", usuario.getRol().getNombre());
            flash.addAttribute("success", usuario.getPersona().getNombre());

            System.out.println("LA PERSONA " + usuario.getPersona().getNombre() + " " + usuario.getPersona().getPaterno() + " " + usuario.getPersona().getMaterno() + " HA INICIADO SESIÓN");

            return "redirect:/vista-administrador";
            
        } else {
            return "redirect:/vista-test";
        }
    }

    @RequestMapping("/cerrar_sesion")
    public String cerrarSesion(HttpServletRequest request, RedirectAttributes flash) {
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        HttpSession sessionAdministrador = request.getSession();
        if (sessionAdministrador != null) {
            sessionAdministrador.invalidate();
            flash.addAttribute("validado", "Se cerro sesion con exito");
            System.out.println("LA PERSONA "+usuarioLogueado.getPersona().getNombre()+" "+usuarioLogueado.getPersona().getPaterno()+" "+usuarioLogueado.getPersona().getMaterno()+ " HA CERRADO SESIÓN");
        }
        return "redirect:/vista-test";
    }
}
