package com.usic.usic.model.IServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usic.usic.model.DTO.PersonaDTO;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IPersonaService;
import com.usic.usic.model.dao.IEstudianteDao;
import com.usic.usic.model.dao.IPersonaDao;

@Service
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    private IPersonaDao personaDao;

    @Autowired
    private IEstudianteDao estudianteDao;

    @Autowired
    private IEstudianteService estudianteService;

    @Override
    public List<Persona> findAll() {
        return personaDao.findAll();
    }

    @Override
    public Persona findById(Long idEntidad) {
        return personaDao.findById(idEntidad).orElse(null);
    }

    @Override
    public Persona save(Persona entidad) {
        return personaDao.save(entidad);
    }

    @Override
    public void deleteById(Long idEntidad) {
        personaDao.deleteById(idEntidad);
    }

    @Override
    public Persona validarCI(String ci) {
        return personaDao.validarCI(ci);
    }

    @Override
    public Persona findByCorreo(String correo) {
        return personaDao.findByCorreo(correo);
    }

    @Override
    public PersonaDTO obtenerPersonaPorCi(String ci) {
        
        Persona persona = personaDao.validarCI(ci);

        Estudiante estudiante = estudianteDao.findByPersonaId(persona.getIdPersona());

        String testRealizado = estudianteService.hasCompletedChasideTest(estudiante.getIdEstudiante());

        return new PersonaDTO(persona.getNombre(), persona.getPaterno(), persona.getMaterno(), persona.getCi(),
        persona.getGenero().getNombreGenero(), persona.getNacionalidad().getNombreNacionalidad(), persona.getCorreo(),
        persona.getFecha(), estudiante.getColegio().getNombreColegio(),testRealizado ,persona.getUrl_certificado());
    }

    @Override
    public Persona buscarPersonaPorCI(String ci) {
        return personaDao.buscarPersonaPorCI(ci);
    }
}
