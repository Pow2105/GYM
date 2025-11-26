package com.GYM.proyecto_software.servicio;

import com.GYM.proyecto_software.modelo.Asistencia;
import com.GYM.proyecto_software.modelo.Cliente;
import com.GYM.proyecto_software.repositorio.AsistenciaRepositorio;
import com.GYM.proyecto_software.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AsistenciaServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    public Asistencia registrarAcceso(String qrCode) {

        Cliente cliente = clienteRepositorio.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ese código QR"));


        Optional<Asistencia> ultimaAsistenciaOpt = asistenciaRepositorio.findTopByClienteOrderByEntradaDesc(cliente);

        if (ultimaAsistenciaOpt.isPresent()) {
            Asistencia ultima = ultimaAsistenciaOpt.get();


            if (ultima.getSalida() == null) {
                ultima.setSalida(LocalDateTime.now());
                ultima.setEstado("FUERA");
                return asistenciaRepositorio.save(ultima);
            }
        }


        Asistencia nuevaAsistencia = new Asistencia();
        nuevaAsistencia.setCliente(cliente);
        nuevaAsistencia.setEntrada(LocalDateTime.now());
        nuevaAsistencia.setEstado("DENTRO");

        return asistenciaRepositorio.save(nuevaAsistencia);
    }
}