package com.GYM.proyecto_software.repositorio;

import com.GYM.proyecto_software.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Long> {
    List<Pago> findByCliente_IdCliente(Long idCliente);
}