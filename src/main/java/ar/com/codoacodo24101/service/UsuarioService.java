package ar.com.codoacodo24101.service;

import java.util.List;

import ar.com.codoacodo24101.dao.UsuarioDao;
import ar.com.codoacodo24101.dao.UsuarioJDBCMysqlImpl;
import ar.com.codoacodo24101.domain.Usuario;
import ar.com.codoacodo24101.dto.UsuarioDto;

public class UsuarioService {
    private UsuarioDao usuarioDao;

    public UsuarioService() {
        this.usuarioDao = new UsuarioJDBCMysqlImpl();
    }

    public List<Usuario> ObtenerTodos() {
        return this.usuarioDao.ObtenerUsuarios();
    }

    public Usuario ObtenerPorId(Long id) {
        return this.usuarioDao.ObtenerUsuarioPorId(id);
    }

    public void Crear(UsuarioDto usuario) {
        usuario.setRol("user");
        this.usuarioDao.CrearUsuario(usuario);
    }

    public Usuario Login(UsuarioDto usuario) {
        return this.usuarioDao.LoginUsuario(usuario);
    }

    public void Modificar(UsuarioDto usuario) {
        List<Usuario> usuarios = this.usuarioDao.ObtenerUsuarios();

        //verificar si el email ya existe
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(usuario.getEmail())) {
                throw new RuntimeException("El email ya existe");
            }
        }

        this.usuarioDao.ModificarUsuario(usuario);
    }

    public void Eliminar(Long id) {
        try {
            this.usuarioDao.EliminarUsuario(id);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo eliminar el usuario");
        }
    }
}
