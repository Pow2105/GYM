@Autowired
private PagoStrategyFactory strategyFactory;

@PostMapping
@Transactional
public Pago registrarPago(@RequestBody PagoRequerido request) {
    Cliente cliente = clienteRepositorio.findById(request.getIdCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    Pago pago = new Pago();
    pago.setCliente(cliente);
    pago.setMonto(request.getMonto());
    pago.setConcepto(request.getConcepto());
    pago.setFecha(LocalDate.now());


    PagoStrategy estrategia = strategyFactory.obtenerEstrategia(request.getConcepto());
    estrategia.procesarPago(pago, cliente);

    return pagoRepositorio.save(pago);
}