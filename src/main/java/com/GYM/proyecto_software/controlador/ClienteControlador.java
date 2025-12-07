package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import com.GYM.proyecto_software.util.QrCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        // Generar UUID si no viene uno (esto es el "texto" único del QR)
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

    // --- NUEVO ENDPOINT PARA GENERAR LA IMAGEN QR ---
    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getClienteQrImage(@PathVariable Long id) {
        return clienteRepositorio.findById(id)
                .map(cliente -> {
                    // Generamos la imagen de 300x300 pixeles usando el UUID del cliente
                    byte[] imagenQr = QrCodeGenerator.getQRCodeImage(cliente.getQrCode(), 300, 300);
                    return ResponseEntity.ok().body(imagenQr);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}