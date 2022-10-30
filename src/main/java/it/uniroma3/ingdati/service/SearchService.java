package it.uniroma3.ingdati.service;

import it.uniroma3.ingdati.service.enums.FieldName;
import it.uniroma3.ingdati.service.vo.DocumentVO;

import java.util.List;

public interface SearchService {
    List<DocumentVO> searchInIndex(String queryString, FieldName fieldName) throws Exception;
    List<DocumentVO> searchForPhraseInIndex(List<String> queryTerms, FieldName fieldName) throws Exception;

}
