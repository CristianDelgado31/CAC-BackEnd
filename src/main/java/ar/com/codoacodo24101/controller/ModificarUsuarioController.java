package ar.com.codoacodo24101.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.codoacodo24101.dto.UsuarioDto;
import ar.com.codoacodo24101.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuario-modificar")
public class ModificarUsuarioController extends HttpServlet{
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader()
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()));

        ObjectMapper mapper = new ObjectMapper();

        UsuarioService service = new UsuarioService();

        try {
            service.Modificar(mapper.readValue(json, UsuarioDto.class));
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().println("Error: " + e);
        }
    }
}
