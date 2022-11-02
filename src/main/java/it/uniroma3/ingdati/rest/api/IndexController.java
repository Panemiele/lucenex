package it.uniroma3.ingdati.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.uniroma3.ingdati.rest.api.dto.DocumentDTO;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/indexes")
public interface IndexController {

    @PostMapping("/default")
    void createIndex() throws IOException;

    @PostMapping
    void createIndex(@RequestParam(value = "filenames") String[] fileNames, @RequestParam("open-mode") String openMode) throws IOException;

    @DeleteMapping
    void deleteIndexes() throws IOException;

}
