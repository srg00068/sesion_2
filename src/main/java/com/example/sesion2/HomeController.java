package com.example.sesion2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {
    private final List<Usuario> ArrayListUsuarios = new ArrayList<>();

@Autowired
    UsuariosDaoInterface dao;


@GetMapping("/")
    public String MetodoGetHTML() {  
 
        return "home";
    }

@GetMapping("/login") //Acceder a la página de login
public String getMethodLogin(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);
    

    if (session != null) {
        Usuario usuario = (Usuario)session.getAttribute("usuario");
        UsuariosDaoJdbc.EstadoUsuario resultado = dao.buscaUsuario(usuario.getUsername(), usuario.getPassword());
        
        switch (resultado) {
            case ADMIN:
            List<Usuario> listaUsers = dao.leeUsuario();
            model.addAttribute("usuarios", listaUsers);
            return "admin";
                
            case NORMAL:
            model.addAttribute("usuario", usuario);
            return "tienda";
        
            case NO_ENCONTRADO:
            String errorMessage = "No existe ese usuario :(";
            model.addAttribute("errorMessage", errorMessage);
            System.out.println("Error al logearse");
            return "login";
        
            default:
            return "login";
        }
    }
    return "login";
}

@PostMapping("/login")
public String postMethodLogin(HttpServletRequest request, Model model) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

//Con JDBC 
UsuariosDaoJdbc.EstadoUsuario resultado = dao.buscaUsuario(username, password);
Usuario usuario = dao.devueleUsuario(username, password);

HttpSession session = request.getSession();
session.setAttribute("usuario", usuario);


switch (resultado) {
    case ADMIN:
    List<Usuario> listaUsers = dao.leeUsuario();
    model.addAttribute("usuarios", listaUsers);
    return "admin";
        
    case NORMAL:
    model.addAttribute("usuario", usuario);
    return "tienda";

    case NO_ENCONTRADO:
    String errorMessage = "No existe ese usuario :(";
    model.addAttribute("errorMessage", errorMessage);
    System.out.println("Error al logearse");
    return "login";

    default:
    return "login";
  
}
}

@GetMapping("/register") //Acceder a la página de registro
public String getMethodRegister(HttpServletRequest request, HttpServletResponse response) {

    return "register";
}

@PostMapping("/register")
public String postMethodString(HttpServletRequest request, HttpServletResponse response, Model model) {
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String role = "normal";

    Usuario usuario = new Usuario(username, email, password, role);
    dao.insertaUsuario(usuario);

    model.addAttribute("usuarios", ArrayListUsuarios);

    return "login";
}

@GetMapping("/tienda")
public String getMethodTienda(HttpServletRequest request, HttpServletResponse response) {

    return "tienda";
}

@GetMapping("/pagohecho")
public String getPagoHecho() {
    
    return "pagohecho";
}

@PostMapping("/pagohecho")
public String postPagoHecho(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);

    if (session != null) {
        Usuario usuario = (Usuario)session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "tienda";
    }
    return "login";
}
}

