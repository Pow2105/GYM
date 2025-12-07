package com.GYM.proyecto_software.security;
import com.GYM.proyecto_software.modelo.Administrador;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.modelo.Entrenador;
import com.GYM.proyecto_software.repositorio.AdministradorRepositorio;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.repositorio.EntrenadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private ClienteRepositorio clienteRepo;
    @Autowired private EntrenadorRepositorio entrenadorRepo;
    @Autowired private AdministradorRepositorio adminRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Buscar en Administradores
        Optional<Administrador> admin = adminRepo.findByEmail(email);
        if (admin.isPresent()) {
            return User.builder()
                    .username(admin.get().getEmail())
                    .password(admin.get().getPassword())
                    .roles("ADMIN")
                    .build();
        }

        // 2. Buscar en Entrenadores
        Optional<Entrenador> entrenador = entrenadorRepo.findByEmail(email);
        if (entrenador.isPresent()) {
            return User.builder()
                    .username(entrenador.get().getEmail())
                    .password(entrenador.get().getPassword())
                    .roles("ENTRENADOR")
                    .build();
        }

        // 3. Buscar en Clientes
        Optional<Cliente> cliente = clienteRepo.findByEmail(email);
        if (cliente.isPresent()) {
            return User.builder()
                    .username(cliente.get().getEmail())
                    .password(cliente.get().getPassword())
                    .roles("CLIENTE")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}