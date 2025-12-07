package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            // 1. Generar QR si no existe
            if (cliente.getQrCode() == null || cliente.getQrCode().isEmpty()) {
                cliente.setQrCode(UUID.randomUUID().toString());
            }

            // 2. Encriptar contraseña
            if (cliente.getPassword() != null && !cliente.getPassword().isEmpty()) {
                cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
            }

            // 3. Asignar Rol por defecto
            cliente.setRol("CLIENTE");

            Cliente nuevoCliente = clienteRepositorio.save(cliente);
            return ResponseEntity.ok(nuevoCliente);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo electrónico ya está registrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el cliente: " + e.getMessage());
        }
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