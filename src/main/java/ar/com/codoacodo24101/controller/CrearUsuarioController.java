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

@WebServlet("/crear-usuario")
public class CrearUsuarioController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader()
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()));

        ObjectMapper mapper = new ObjectMapper();

        UsuarioService service = new UsuarioService();

        try {
            UsuarioDto usuario = mapper.readValue(json, UsuarioDto.class);
            service.Crear(usuario);
            resp.setStatus(201);
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().println("Error: " + e);
        }
    }
    
}
