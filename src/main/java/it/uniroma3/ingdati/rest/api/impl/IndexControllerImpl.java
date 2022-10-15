package it.uniroma3.ingdati.rest.api.impl;

import it.uniroma3.ingdati.rest.api.IndexController;
import it.uniroma3.ingdati.service.IndexService;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class IndexControllerImpl implements IndexController {

    private final IndexService indexService;

    public IndexControllerImpl(IndexService indexService){
        this.indexService = indexService;
    }

    @Override
    public void createIndex() throws IOException {
        indexService.createIndex();
    }
}
