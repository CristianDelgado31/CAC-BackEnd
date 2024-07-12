package ar.com.codoacodo24101.dao;

import java.util.List;

import ar.com.codoacodo24101.domain.Usuario;
import ar.com.codoacodo24101.dto.UsuarioDto;

public interface UsuarioDao {

    public List<Usuario> ObtenerUsuarios();

    public Usuario ObtenerUsuarioPorId(Long id);

    public void CrearUsuario(UsuarioDto usuarioDto);

    public Usuario LoginUsuario(UsuarioDto usuarioDto);

    public void ModificarUsuario(UsuarioDto usuarioDto);

    public void EliminarUsuario(Long id);

}
