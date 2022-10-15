package it.uniroma3.ingdati.rest.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("/indexes")
public interface IndexController {
    @PostMapping
    void createIndex() throws IOException;
}
