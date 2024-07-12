package ar.com.codoacodo24101.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.com.codoacodo24101.domain.Usuario;
import ar.com.codoacodo24101.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/obtener-usuario/*")
public class ObtenerUsuarioController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener el ID de la URL (después de /obtener-usuario/)
        String pathInfo = req.getPathInfo(); // Esto obtiene la parte /1
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el ID del usuario");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1)); // Quita la barra inicial y convierte a Long
            UsuarioService service = new UsuarioService();
            Usuario usuario = service.ObtenerPorId(id);

            if (usuario == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            // Para que pueda serializar la fecha
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String usuarioJson = mapper.writeValueAsString(usuario);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(usuarioJson);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario inválido");
        }
    }
}