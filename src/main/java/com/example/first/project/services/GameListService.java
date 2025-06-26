package com.example.first.project.services;

import com.example.first.project.dto.GameListDTO;
import com.example.first.project.entities.GameList;
import com.example.first.project.projections.GameMinProjection;
import com.example.first.project.repositories.GameListRepository;
import com.example.first.project.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll (){
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(x -> new GameListDTO(x)).toList();
    }

    //Criando método que repociona os elementos de uma lista.

    @Transactional
    public void move(Long listId, int sourceIndex, int destinationIndex){
        //dado esses argumentos, eu consigo condição para atualizar minha lista;

        //primeiro passo, buscar da mémoria essa lista;
        List<GameMinProjection> list = gameListRepository.searchByList(listId);

        //segundo passo, removendo um objeto da sua posição de origem;
        GameMinProjection obj = list.remove(sourceIndex);

        //terceiro passo, adicionando o objeto novamente na lista (destino);
        list.add(destinationIndex, obj);

        //Se fizer o reposicionamento em memória na lista, usando o list do java, já pego a posição nova de cada elemento.
        //Com esse macete, de usar as novas posições na lista para salvar no banco de dados, no tb_belonging para atualizar a coluna position;
        //Dado o ID da lista e o ID do jogo, atualiza o position dele.

        int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
        int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;

        for (int i = min; i <= max; i++){
            gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
        }

    }
}
