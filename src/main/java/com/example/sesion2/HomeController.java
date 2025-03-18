package com.example.sesion2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
        String username = (String)session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        
        model.addAttribute("username", username);
        model.addAttribute("password", password);

        return "tienda";
    }

    return "login";
}

@PostMapping("/login")
public String postMethodLogin(HttpServletRequest request, Model model) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    if (username.equals("sergio") && password.equals("1234")) {
        Usuario usuario = new Usuario("sergio", ".", "1234");
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("password", password);

        //Añado los astributos de la sesión a la siguiente pagina
        model.addAttribute("usuario", usuario );

        return "tienda";

    } else if(username.equals("admin") && password.equals("admin")){
        
        List<Usuario> listaUsers = dao.leeUsuario();
        model.addAttribute("usuarios", listaUsers);
       return "admin";
    }

    String errorMessage = "No existe ese usuario :(";
    model.addAttribute("errorMessage", errorMessage);
    System.out.println("Error al logearse");

    return "login";
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

    Usuario usuario = new Usuario(username, email, password);

    //UsuariosDaoJdbc dao = new UsuariosDaoJdbc();
    dao.insertaUsuario(usuario);

    ArrayListUsuarios.add(usuario);
    model.addAttribute("usuarios", ArrayListUsuarios);

    return "login";
}

@GetMapping("/tienda")
public String getMethoTienda(HttpServletRequest request, HttpServletResponse response) {


    return "tienda";
}



}

