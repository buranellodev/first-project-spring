package com.example.first.project.dto;

import com.example.first.project.entities.GameList;

public class GameListDTO {
    private Long id;
    private String nome;

    public GameListDTO(){
    }

    public GameListDTO(GameList entity){
        id = entity.getId();
        nome = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
