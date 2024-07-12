package ar.com.codoacodo24101.controller;

import java.io.IOException;

import ar.com.codoacodo24101.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuario-eliminar/*")
public class EliminarUsuarioController extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // Esto obtiene la parte /1
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el ID del usuario");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1)); // Quita la barra inicial y convierte a Long
            UsuarioService service = new UsuarioService();
            service.Eliminar(id);

            resp.setStatus(HttpServletResponse.SC_OK); // 200 
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario inválido");
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
