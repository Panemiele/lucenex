package it.uniroma3.ingdati.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.uniroma3.ingdati.rest.api.dto.DocumentDTO;
import it.uniroma3.ingdati.rest.api.dto.SearchRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/search")
public interface SearchController {

    @PostMapping("/term")
    List<DocumentDTO> searchInIndex(SearchRequestDTO searchRequestDTO) throws Exception;

    @PostMapping("/phrase")
    List<DocumentDTO> searchForPhraseInIndex(@RequestBody SearchRequestDTO searchRequestDTO) throws Exception;
}
