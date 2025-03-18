package com.example.sesion2;

import java.util.List;

public interface UsuariosDaoInterface {
    public List<Usuario> leeUsuario();
    public boolean insertaUsuario(Usuario usuario);

}
