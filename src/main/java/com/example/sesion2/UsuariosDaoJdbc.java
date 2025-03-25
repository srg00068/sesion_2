package com.example.sesion2;

import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuariosDaoJdbc implements UsuariosDaoInterface {


    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


   private final RowMapper<Usuario> mapper = (rs, numRow) -> {
        Usuario usuario = new Usuario();
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail((rs.getString("email")));
        usuario.setRole(rs.getString("role"));
        return usuario;
    };


    @Override
    public List<Usuario> leeUsuario() {
        String sql = "SELECT * FROM users";
        List<Usuario> usuarios = this.jdbcTemplate.query(sql, mapper);
        return usuarios;      
    }

    @Override
    public boolean insertaUsuario(Usuario usuario) {
        String sql = "insert into users (username,email,password, role) values(?, ?, ?, ?)";
        int nfilas = this.jdbcTemplate.update(sql, usuario.getUsername(), usuario.getEmail(), usuario.getPassword(),"normal");
            return (nfilas == 1);
    }

    @Override
    public EstadoUsuario buscaUsuario(String user, String password) {
        String sql = "select * from users where username = ? and password = ?";
        List<Usuario> usuarios = this.jdbcTemplate.query(sql, mapper, user, password);

        if (usuarios.isEmpty()) {
            return EstadoUsuario.NO_ENCONTRADO;
        }
        
        Usuario usuario = usuarios.get(0);

        if (usuario.getRole() == null) {
            return EstadoUsuario.NO_ENCONTRADO; 
        }

        switch (usuario.getRole()) {
            case "admin":
                return EstadoUsuario.ADMIN;
            case "normal":
                return EstadoUsuario.NORMAL;
            default:
                return EstadoUsuario.NO_ENCONTRADO;
        }
    }

    public enum EstadoUsuario {
        ADMIN, NORMAL, NO_ENCONTRADO
    }

    @Override
    public Usuario devueleUsuario(String user, String password) {
        String sql = "select * from users where username = ? and password = ?";
        List<Usuario> usuarios = this.jdbcTemplate.query(sql, mapper, user, password);
        if (usuarios.isEmpty()) {
            return new Usuario();
        }
        Usuario usuario = usuarios.get(0);
        return usuario;
    }

}
