package com.GYM.proyecto_software.repositorio;
import com.GYM.proyecto_software.modelo.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RutinaRepositorio extends JpaRepository<Rutina, Long> {
    List<Rutina> findByCliente_IdCliente(Long idCliente);
}