package ar.com.codoacodo24101.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.com.codoacodo24101.domain.Usuario;
import ar.com.codoacodo24101.dto.UsuarioDto;

public class UsuarioJDBCMysqlImpl implements UsuarioDao {

    @Override
    public Usuario ObtenerUsuarioPorId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = " + id;

        Usuario usuario = null;
        Connection connection = null;

        try {
            connection = AdministradorDeConexiones.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Long usuarioId = resultSet.getLong("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String email = resultSet.getString("email");
                String contrasenia = resultSet.getString("contrasenia");
                LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate();
                String pais = resultSet.getString("pais");
                String rol = resultSet.getString("rol");

                usuario = new Usuario(usuarioId, nombre, apellido, email, contrasenia, fechaNacimiento, pais);
                usuario.setRol(rol);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        } finally {
            AdministradorDeConexiones.desconectar(connection);
        }

        return usuario;
    }

    @Override
    public void CrearUsuario(UsuarioDto usuarioDto) {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, contrasenia, fecha_nacimiento, pais, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;

        try {
            connection = AdministradorDeConexiones.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuarioDto.getNombre());
            statement.setString(2, usuarioDto.getApellido());
            statement.setString(3, usuarioDto.getEmail());
            statement.setString(4, usuarioDto.getContrasenia());
            statement.setString(5, usuarioDto.getFechaNacimiento());
            statement.setString(6, usuarioDto.getPais());
            statement.setString(7, usuarioDto.getRol());

            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        } finally {
            AdministradorDeConexiones.desconectar(connection);
        }
    }

    @Override
    public List<Usuario> ObtenerUsuarios() {
        String sql = "SELECT * FROM usuarios";

        List<Usuario> usuarios = new ArrayList<>();

        Connection connection = null;

        try {
            connection = AdministradorDeConexiones.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long usuarioId = resultSet.getLong("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String email = resultSet.getString("email");
                String contrasenia = resultSet.getString("contrasenia");
                LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate();
                String pais = resultSet.getString("pais");
                String rol = resultSet.getString("rol");

                Usuario usuario = new Usuario(usuarioId, nombre, apellido, email, contrasenia, fechaNacimiento, pais);
                usuario.setRol(rol);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        } finally {
            AdministradorDeConexiones.desconectar(connection);
        }

        return usuarios;
    }

    @Override
    public Usuario LoginUsuario(UsuarioDto usuarioDto) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasenia = ?";

        Usuario usuario = null;
        Connection connection = null;

        try {
            connection = AdministradorDeConexiones.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuarioDto.getEmail());
            statement.setString(2, usuarioDto.getContrasenia());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Long usuarioId = resultSet.getLong("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String email = resultSet.getString("email");
                String contrasenia = resultSet.getString("contrasenia");
                LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate();
                String pais = resultSet.getString("pais");
                String rol = resultSet.getString("rol");

                usuario = new Usuario(usuarioId, nombre, apellido, email, contrasenia, fechaNacimiento, pais);
                usuario.setRol(rol);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        } finally {
            AdministradorDeConexiones.desconectar(connection);
        }

        return usuario; // null si no se encuentra el usuario
    }

    @Override
    public void ModificarUsuario(UsuarioDto usuarioDto) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, contrasenia = ?, fecha_nacimiento = ?, pais = ? WHERE id = ?";

        Connection connection = null;

        try {
            connection = AdministradorDeConexiones.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuarioDto.getNombre());
            statement.setString(2, usuarioDto.getApellido());
            statement.setString(3, usuarioDto.getEmail());
            statement.setString(4, usuarioDto.getContrasenia());
            statement.setString(5, usuarioDto.getFechaNacimiento());
            statement.setString(6, usuarioDto.getPais());
            statement.setLong(7, usuarioDto.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        } finally {
            AdministradorDeConexiones.desconectar(connection);
        }
    }

    @Override
    public void EliminarUsuario(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        Connection connection = null;

        try {
            connection = AdministradorDeConexiones.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        } finally {
            AdministradorDeConexiones.desconectar(connection);
        }
    }
    
}
