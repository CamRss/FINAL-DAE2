package pe.com.Colegio.Euler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pe.com.Colegio.Euler.entity.cursoEntity;
import pe.com.Colegio.Euler.servicio.aulaService;
import pe.com.Colegio.Euler.servicio.cursoService;
import pe.com.Colegio.Euler.servicio.horarioService;
import pe.com.Colegio.Euler.servicio.profesorService;

@Controller
public class cursoController {

    @Autowired
    private cursoService servicio;

    @Autowired
    private aulaService servicioAula;

    @Autowired
    private profesorService servicioProfesor;

    @Autowired
    private horarioService servicioHorario;

    // Listar cursos
    @GetMapping("/curso/listar")
    public String MostrarListarCurso(Model modelo) {
        modelo.addAttribute("cursos", servicio.findAllCustom());
        return "curso/listar_curso";
    }

    // Mostrar formulario para registrar un curso
    @GetMapping("/curso/registro")
    public String MostrarRegistroCurso(Model modelo) {
        modelo.addAttribute("aulas", servicioAula.findAllCustom());
        modelo.addAttribute("profesores", servicioProfesor.findAllCustom());
        modelo.addAttribute("horarios", servicioHorario.findAllCustom());
        return "curso/registrar_curso";
    }

    // Mostrar formulario para actualizar un curso
    @GetMapping("/curso/actualiza/{id}")
    public String MostrarActualizaCurso(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("aulas", servicioAula.findAllCustom());
        modelo.addAttribute("profesores", servicioProfesor.findAllCustom());
        modelo.addAttribute("horarios", servicioHorario.findAllCustom());
        modelo.addAttribute("cursos", servicio.findById(id));
        return "curso/actualizar_curso";
    }

    // Eliminar un curso (Deshabilitar)
    @GetMapping("/curso/eliminar/{id}")
    public String EliminarCurso(@PathVariable Long id) {
        cursoEntity objCurso = new cursoEntity();
        objCurso.setCodigo(id);
        servicio.delete(objCurso);
        return "redirect:/curso/listar";
    }

    // Mostrar cursos habilitados y deshabilitados
    @GetMapping("/curso/habilita")
    public String MostrarHabilitarCurso(Model modelo) {
        modelo.addAttribute("cursos", servicio.findAll());
        return "curso/habilitar_curso";
    }

    @GetMapping("/curso/habilitar/{id}")
    public String HabilitarCurso(@PathVariable Long id) {
        cursoEntity objCurso = new cursoEntity();
        objCurso.setCodigo(id);
        servicio.enable(objCurso);
        return "redirect:/curso/habilita";
    }

    @GetMapping("/curso/deshabilitar/{id}")
    public String DeshabilitarCurso(@PathVariable Long id) {
        cursoEntity objCurso = new cursoEntity();
        objCurso.setCodigo(id);
        servicio.delete(objCurso);
        return "redirect:/curso/habilita";
    }

    // Modelo para transportar datos del formulario
    @ModelAttribute("curso")
    public cursoEntity ModeloCurso() {
        return new cursoEntity();
    }

    // Acción para registrar un curso
    @PostMapping("/curso/registrar")
    public String RegistrarCurso(@ModelAttribute("curso") cursoEntity c) {
        servicio.add(c);
        return "redirect:/curso/listar";
    }

    // Acción para actualizar un curso
    @PostMapping("/curso/actualizar/{id}")
    public String ActualizarCurso(@PathVariable Long id, @ModelAttribute("curso") cursoEntity c) {
        c.setCodigo(id);
        servicio.update(c);
        return "redirect:/curso/listar";
    }
}
