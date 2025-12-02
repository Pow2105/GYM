package com.GYM.proyecto_software.repositorio;

import com.GYM.proyecto_software.modelo.UsoMaquinaExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsoMaquinaExtraRepositorio extends JpaRepository<UsoMaquinaExtra, Long> {


    List<UsoMaquinaExtra> findByCliente_IdCliente(Long idCliente);
}