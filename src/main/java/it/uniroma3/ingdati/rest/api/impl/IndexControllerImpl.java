package it.uniroma3.ingdati.rest.api.impl;

import it.uniroma3.ingdati.rest.api.IndexController;
import it.uniroma3.ingdati.service.IndexService;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IndexControllerImpl implements IndexController {

    private final IndexService indexService;

    public IndexControllerImpl(IndexService indexService){
        this.indexService = indexService;
    }

    // http://localhost:8080/ing-dati/indexes
    @Override public void createIndex() throws IOException {
        indexService.createIndex();
    }

    // http://localhost:8080/ing-dati/indexes/multiple?filenames=Come_diventare_un_ingegnere_dei_dati&filenames=Curriculum_Ingegneria_dei_Dati
    @Override public void createIndex(String[] fileNames) throws IOException {
        indexService.createIndex(fileNames);
    }


    // http://localhost:8080/ing-dati/indexes
    @Override public void deleteIndexes() throws IOException {
        indexService.deleteIndexes();
    }
}
