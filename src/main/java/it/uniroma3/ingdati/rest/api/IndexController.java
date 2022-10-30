package it.uniroma3.ingdati.rest.api;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/indexes")
public interface IndexController {
    @PostMapping
    void createIndex() throws IOException;

    @PostMapping("/multiple")
    void createIndex(@RequestParam(value = "filenames") String[] fileNames, @RequestParam("open-mode") String openMode) throws IOException;

    @DeleteMapping
    void deleteIndexes() throws IOException;

}
