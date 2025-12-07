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
@CrossOrigin(origins = "*")
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
            if (cliente.getQrCode() == null || cliente.getQrCode().isEmpty()) {
                cliente.setQrCode(UUID.randomUUID().toString());
            }
            if (cliente.getPassword() != null && !cliente.getPassword().isEmpty()) {
                cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
            }
            cliente.setRol("CLIENTE");

            Cliente nuevoCliente = clienteRepositorio.save(cliente);
            return ResponseEntity.ok(nuevoCliente);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya existe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Cliente detalles) {
        return clienteRepositorio.findById(id).map(cliente -> {
            cliente.setNombre(detalles.getNombre());
            cliente.setTelefono(detalles.getTelefono());
            cliente.setTipoCliente(detalles.getTipoCliente());
            // Solo actualizamos contraseña si envían una nueva
            if (detalles.getPassword() != null && !detalles.getPassword().isEmpty()) {
                cliente.setPassword(passwordEncoder.encode(detalles.getPassword()));
            }
            return ResponseEntity.ok(clienteRepositorio.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        if (!clienteRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepositorio.deleteById(id);
        return ResponseEntity.ok().build();
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