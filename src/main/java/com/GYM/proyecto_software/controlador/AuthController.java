package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Administrador;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Entrenador;
import com.GYM.proyecto_software.repositorio.AdministradorRepositorio;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.EntrenadorRepositorio;
import com.GYM.proyecto_software.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtUtil jwtUtil;

    // Repositorios para devolver los datos completos del usuario tras el login
    @Autowired private ClienteRepositorio clienteRepo;
    @Autowired private EntrenadorRepositorio entrenadorRepo;
    @Autowired private AdministradorRepositorio adminRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        try {
            // 1. Autenticar con Spring Security (Esto verifica la contraseña encriptada)
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            // 2. Cargar detalles y generar Token
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String rol = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            String token = jwtUtil.generateToken(userDetails.getUsername(), rol);

            // 3. Obtener el objeto usuario real para enviarlo al frontend
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("token", token);
            respuesta.put("rol", rol);

            if (rol.equals("CLIENTE")) {
                Optional<Cliente> c = clienteRepo.findByEmail(email);
                c.ifPresent(val -> respuesta.put("usuario", val));
            } else if (rol.equals("ENTRENADOR")) {
                Optional<Entrenador> e = entrenadorRepo.findByEmail(email);
                e.ifPresent(val -> respuesta.put("usuario", val));
            } else if (rol.equals("ADMIN")) {
                Optional<Administrador> a = adminRepo.findByEmail(email);
                a.ifPresent(val -> respuesta.put("usuario", val));
            }

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Credenciales inválidas");
        }
    }
}