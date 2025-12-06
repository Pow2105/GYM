package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.UsoMaquinaExtra;
import com.GYM.proyecto_software.repositorio.UsoMaquinaExtraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uso-extra")
@CrossOrigin(origins = "*")
public class UsoMaquinaExtraControlador {

    @Autowired
    private UsoMaquinaExtraRepositorio usoMaquinaExtraRepositorio;

    // Ver todo el historial de usos extra
    @GetMapping
    public List<UsoMaquinaExtra> getAllUsosExtra() {
        return usoMaquinaExtraRepositorio.findAll();
    }

    // Ver historial de usos extra de un cliente específico
    @GetMapping("/cliente/{idCliente}")
    public List<UsoMaquinaExtra> getUsosPorCliente(@PathVariable Long idCliente) {
        return usoMaquinaExtraRepositorio.findByCliente_IdCliente(idCliente);
    }

    // Consultar detalle de un uso específico
    @GetMapping("/{id}")
    public ResponseEntity<UsoMaquinaExtra> getUsoById(@PathVariable Long id) {
        return usoMaquinaExtraRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}