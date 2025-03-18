package com.example.sesion2;

import java.util.List;

import com.example.sesion2.UsuariosDaoJdbc.EstadoUsuario;

public interface UsuariosDaoInterface {
    public List<Usuario> leeUsuario();
    public boolean insertaUsuario(Usuario usuario);
    public EstadoUsuario buscaUsuario(String user,String password);
    public Usuario devueleUsuario(String user,String password);


}
