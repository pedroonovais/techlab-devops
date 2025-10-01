package com.techlab.helper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface base para services que definem contratos comuns
 * para operações CRUD e conversões DTO/Entity.
 * 
 * @param <T> Tipo da entidade
 * @param <D> Tipo do DTO
 */
public interface BaseService<T, D> {
    
    /**
     * Busca todas as entidades e retorna como DTOs
     * @return Lista de DTOs
     */
    List<D> findAll();
    
    /**
     * Busca entidade por ID e retorna como DTO
     * @param id ID da entidade
     * @return DTO da entidade
     */
    D findById(Long id);
    
    /**
     * Salva ou atualiza uma entidade através do DTO
     * @param dto DTO com os dados
     * @return DTO da entidade salva
     */
    D save(D dto);
    
    /**
     * Remove entidade por ID
     * @param id ID da entidade
     */
    void delete(Long id);
    
    /**
     * Converte entidade para DTO
     * @param entity Entidade
     * @return DTO
     */
    D toDTO(T entity);
    
    /**
     * Converte DTO para entidade
     * @param dto DTO
     * @return Entidade
     */
    T toEntity(D dto);
    
    /**
     * Converte lista de entidades para lista de DTOs
     * @param entities Lista de entidades
     * @return Lista de DTOs
     */
    default List<D> toDTOList(List<T> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}