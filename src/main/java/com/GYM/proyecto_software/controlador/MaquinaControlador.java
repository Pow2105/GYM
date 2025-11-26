package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Maquina;
import com.GYM.proyecto_software.repositorio.MaquinaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maquinas")
@CrossOrigin(origins = "*")
public class MaquinaControlador {

    @Autowired
    private MaquinaRepositorio maquinaRepositorio;

    @GetMapping
    public List<Maquina> getAllMaquinas() {
        return maquinaRepositorio.findAll();
    }

    @PostMapping
    public Maquina createMaquina(@RequestBody Maquina maquina) {
        return maquinaRepositorio.save(maquina);
    }

    @PutMapping("/{id}")
    public Maquina updateMaquina(@PathVariable Long id, @RequestBody Maquina detalles) {
        return maquinaRepositorio.findById(id).map(maquina -> {
            maquina.setNombre(detalles.getNombre());
            maquina.setTipo(detalles.getTipo());
            maquina.setEstado(detalles.getEstado());
            return maquinaRepositorio.save(maquina);
        }).orElseThrow(() -> new RuntimeException("Máquina no encontrada"));
    }
}