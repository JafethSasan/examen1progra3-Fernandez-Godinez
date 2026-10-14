/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.controller;

/**
 *
 * @author jafet
 */
import cr.ac.una.model.Prestamo;
import cr.ac.una.service.PrestamoService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Fachada de casos de uso para préstamos. */
public class PrestamoController {
    private final PrestamoService service;
    public PrestamoController(PrestamoService service) { this.service=service; }
    public Prestamo registrar(String idSolicitante,String codigoEquipo,LocalDate fechaEsperada) { return service.registrar(idSolicitante,codigoEquipo,fechaEsperada); }
    public void devolver(int id) { service.devolver(id); }
    public Optional<Prestamo> buscar(int id) { return service.buscar(id); }
    public List<Prestamo> listar() { return service.listar(); }
    public List<Prestamo> listarActivosPorSolicitante(String id) { return service.listarActivosPorSolicitante(id); }
}