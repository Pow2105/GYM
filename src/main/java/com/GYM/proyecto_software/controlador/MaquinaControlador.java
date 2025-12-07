package com.GYM.proyecto_software.controlador;

import com.GYM.proyecto_software.modelo.Maquina;
import com.GYM.proyecto_software.repositorio.MaquinaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        if (maquina.getEstado() == null) maquina.setEstado("DISPONIBLE");
        return maquinaRepositorio.save(maquina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maquina> updateMaquina(@PathVariable Long id, @RequestBody Maquina detalles) {
        return maquinaRepositorio.findById(id).map(maquina -> {
            maquina.setNombre(detalles.getNombre());
            maquina.setTipo(detalles.getTipo());
            maquina.setEstado(detalles.getEstado());
            return ResponseEntity.ok(maquinaRepositorio.save(maquina));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaquina(@PathVariable Long id) {
        if (!maquinaRepositorio.existsById(id)) return ResponseEntity.notFound().build();
        maquinaRepositorio.deleteById(id);
        return ResponseEntity.ok().build();
    }
}