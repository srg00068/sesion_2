package com.example.sesion2;

import java.sql.ResultSet;
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
        return usuario;
    };


    @Override
    public List<Usuario> leeUsuario() {
        String sql = "SELECT username, password FROM users";
        List<Usuario> usuarios = this.jdbcTemplate.query(sql, mapper);
        return usuarios;      
    }

    @Override
    public boolean insertaUsuario(Usuario usuario) {
        String sql = "insert into users (username,email,password) values(?, ?, ?)";
        int nfilas = this.jdbcTemplate.update(sql, usuario.getUsername(), usuario.getEmail(), usuario.getPassword());
            return (nfilas == 1);
        
    }

}
