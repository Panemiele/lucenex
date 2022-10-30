package it.uniroma3.ingdati.rest.api.impl;

import it.uniroma3.ingdati.rest.api.SearchController;
import it.uniroma3.ingdati.rest.api.dto.DocumentDTO;
import it.uniroma3.ingdati.rest.api.dto.SearchRequestDTO;
import it.uniroma3.ingdati.service.SearchService;
import it.uniroma3.ingdati.service.enums.FieldName;
import it.uniroma3.ingdati.service.vo.DocumentVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SearchControllerImpl implements SearchController {

    private final SearchService searchService;

    public SearchControllerImpl(SearchService searchService){
        this.searchService = searchService;
    }

    @Override public List<DocumentDTO> searchInIndex(@RequestBody SearchRequestDTO searchRequestDTO) throws Exception {
        String queryString = searchRequestDTO.getQueryStrings().get(0);
        FieldName fieldName = FieldName.TITLE.name().equals(searchRequestDTO.getFieldName()) ? FieldName.TITLE : FieldName.CONTENT;
        List<DocumentVO> documentVOList = searchService.searchInIndex(queryString, fieldName);
        return Optional.ofNullable(documentVOList)
            .orElse(new ArrayList<>())
            .stream()
            .map(vo -> new DocumentDTO(vo.getId(), vo.getTitle(), vo.getRankWeight()))
            .collect(Collectors.toList());
    }

    @Override public List<DocumentDTO> searchForPhraseInIndex(@RequestBody SearchRequestDTO searchRequestDTO) throws Exception {
        List<String> queryTerms = searchRequestDTO.getQueryStrings();
        FieldName fieldName = FieldName.TITLE.name().equals(searchRequestDTO.getFieldName()) ? FieldName.TITLE : FieldName.CONTENT;
        List<DocumentVO> documentVOList = searchService.searchForPhraseInIndex(queryTerms, fieldName);
        return Optional.ofNullable(documentVOList)
            .orElse(new ArrayList<>())
            .stream()
            .map(vo -> new DocumentDTO(vo.getId(), vo.getTitle(), vo.getRankWeight()))
            .collect(Collectors.toList());
    }
}
