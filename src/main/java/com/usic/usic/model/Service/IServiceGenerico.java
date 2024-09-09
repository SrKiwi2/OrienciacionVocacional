package com.usic.usic.model.Service;

import java.util.List;

public interface IServiceGenerico <T, K>{
    
    List<T> findAll();

    T findById(K idEntidad);

    T save(T entidad);

    void deleteById(K idEntidad);
}
