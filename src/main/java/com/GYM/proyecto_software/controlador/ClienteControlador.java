package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.util.QrCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepositorio.findAll();
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        if (cliente.getQrCode() == null || cliente.getQrCode().isEmpty()) {
            cliente.setQrCode(UUID.randomUUID().toString());
        }
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        return clienteRepositorio.findByEmail(email)
                .filter(c -> c.getPassword() != null && c.getPassword().equals(password))
                .map(cliente -> {
                    // Retornamos el cliente completo
                    return ResponseEntity.ok(cliente);
                })
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getClienteQrImage(@PathVariable Long id) {
        return clienteRepositorio.findById(id)
                .map(cliente -> {
                    byte[] imagenQr = QrCodeGenerator.getQRCodeImage(cliente.getQrCode(), 300, 300);
                    return ResponseEntity.ok().body(imagenQr);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}