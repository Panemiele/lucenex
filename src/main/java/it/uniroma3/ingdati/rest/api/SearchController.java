package it.uniroma3.ingdati.rest.api;

import it.uniroma3.ingdati.rest.api.dto.DocumentDTO;
import it.uniroma3.ingdati.rest.api.dto.SearchRequestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/search")
public interface SearchController {

    @GetMapping
    List<DocumentDTO> searchInIndex(SearchRequestDTO searchRequestDTO) throws Exception;
}
