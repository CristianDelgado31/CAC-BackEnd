package ar.com.codoacodo24101.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.com.codoacodo24101.domain.Usuario;
import ar.com.codoacodo24101.dto.UsuarioDto;
import ar.com.codoacodo24101.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginController extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = req.getReader()
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()));

        ObjectMapper mapper = new ObjectMapper();
        // Para que pueda serializar la fecha
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        UsuarioService service = new UsuarioService();

        try {
            UsuarioDto usuario = mapper.readValue(json, UsuarioDto.class);
            Usuario usuarioLogueado = service.Login(usuario);
            if (usuarioLogueado != null) {
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().println(mapper.writeValueAsString(usuarioLogueado));
            } else {
                resp.setStatus(401);
                resp.getWriter().println("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().println("Error: " + e);
        }
    }
}
