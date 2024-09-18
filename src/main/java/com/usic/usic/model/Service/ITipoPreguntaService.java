package com.usic.usic.model.Service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.usic.usic.model.Entity.TipoPregunta;

@Service
public interface ITipoPreguntaService extends IServiceGenerico<TipoPregunta, Long>{
    List<TipoPregunta> getTipoPreguntaByRespuestaId(Long idRespuesta);
}
