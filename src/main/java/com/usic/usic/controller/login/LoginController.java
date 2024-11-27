package com.usic.usic.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usic.usic.model.Entity.Usuario;
import com.usic.usic.model.Service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private IUsuarioService  usuarioService;

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<?> iniciarSesion(@RequestParam(value = "usuario") String user,
                            @RequestParam(value = "contrasena") String contrasena, 
                            Model model, HttpServletRequest request,
                            RedirectAttributes flash) {

        Usuario usuario = usuarioService.getUsuarioPassword(user, contrasena);

        if (usuario != null) {

            if (usuario.getEstado().equals("INHABILITADO")) {
                return ResponseEntity.ok("Este usuario no esta habilitado");
            }

            if (usuario.getRol().getNombre().equals("ESTUDIANTES")) {

                HttpSession sessionEstudiantes = request.getSession(true);
                sessionEstudiantes.setAttribute("usuario", usuario);
                sessionEstudiantes.setAttribute("persona", usuario.getPersona());
                sessionEstudiantes.setAttribute("nombre_rol", usuario.getRol().getNombre());
                flash.addAttribute("success", usuario.getPersona().getNombre());
                
                return ResponseEntity.ok("login estudiante");

            }else{
                HttpSession sessionAdministrador = request.getSession(true);
                sessionAdministrador.setAttribute("usuario", usuario);
                sessionAdministrador.setAttribute("persona", usuario.getPersona());
                sessionAdministrador.setAttribute("nombre_rol", usuario.getRol().getNombre());
                flash.addAttribute("success", usuario.getPersona().getNombre());
                return ResponseEntity.ok("login administrador");
            }

        } else {
            return ResponseEntity.ok("no existe el usuario");
        }
    }

    @RequestMapping("/cerrar_sesion")
    public String cerrarSesion(HttpServletRequest request, RedirectAttributes flash) {
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
            
            if (usuarioLogueado != null) {
                System.out.println("LA PERSONA "+usuarioLogueado.getPersona().getNombre()+" "+usuarioLogueado.getPersona().getPaterno()+" "+usuarioLogueado.getPersona().getMaterno()+ " HA CERRADO SESIÓN");
            }

            flash.addAttribute("validado", "Se cerro sesion con exito");
        }
        return "redirect:/orientacion_vocacional";
    }
}
