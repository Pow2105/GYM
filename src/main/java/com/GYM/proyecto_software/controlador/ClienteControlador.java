package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección para encriptar

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepositorio.findAll();
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        // 1. Generar QR si no existe
        if (cliente.getQrCode() == null || cliente.getQrCode().isEmpty()) {
            cliente.setQrCode(UUID.randomUUID().toString());
        }

        // 2. Encriptar contraseña (CRUCIAL para Spring Security)
        if (cliente.getPassword() != null && !cliente.getPassword().isEmpty()) {
            cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        }

        // 3. Asignar Rol por defecto
        cliente.setRol("CLIENTE");

        return clienteRepositorio.save(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Cliente> getClienteByEmail(@RequestParam String email) {
        return clienteRepositorio.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para ver la imagen del QR
    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getClienteQrImage(@PathVariable Long id) {
        return clienteRepositorio.findById(id)
                .map(cliente -> {
                    byte[] imagenQr = QRCodeGenerator.getQRCodeImage(cliente.getQrCode(), 300, 300);
                    return ResponseEntity.ok().body(imagenQr);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}