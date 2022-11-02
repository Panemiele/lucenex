package it.uniroma3.ingdati.rest.api.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.ingdati.rest.api.IndexController;
import it.uniroma3.ingdati.service.IndexService;
import org.apache.lucene.index.IndexWriterConfig;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Tag(name = "IndexController", description = "Defines methods for Lucene indexes management")
@Schema(name = "IndexController", description = "Defines methods for Lucene indexes management")
public class IndexControllerImpl implements IndexController {

    private final IndexService indexService;

    public IndexControllerImpl(IndexService indexService){
        this.indexService = indexService;
    }


    @Override
    @Operation(description = "Create a default Lucene index",
        responses = {
            @ApiResponse(
                responseCode = "200", description = "Index created succesfully"
            )
        }
    )
    public void createIndex() throws IOException {
        indexService.createIndex();
    }


    @Override
    @Operation(description = "Create a Lucene index using the input files names",
        responses = {
            @ApiResponse(
                responseCode = "200", description = "Index created succesfully"
            )
        }
    )
    public void createIndex(String[] fileNames, String openModeName) throws IOException {
        IndexWriterConfig.OpenMode openMode = mapToOpenMode(openModeName);
        indexService.createIndex(fileNames, openMode);
    }


    @Override
    @Operation(description = "Deletes all the Lucene indexes",
        responses = {
            @ApiResponse(
                responseCode = "200", description = "Index created succesfully"
            )
        }
    )
    public void deleteIndexes() throws IOException {
        indexService.deleteIndexes();
    }


    /*-----------------*
     * PRIVATE METHODS *
     *-----------------*/

    private IndexWriterConfig.OpenMode mapToOpenMode(String openModeName){
        IndexWriterConfig.OpenMode openMode;
        switch (openModeName){
            case "CREATE":
                openMode = IndexWriterConfig.OpenMode.CREATE;
                break;
            case "APPEND":
                openMode = IndexWriterConfig.OpenMode.APPEND;
                break;
            case "CREATE_OR_APPEND":
                openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND;
                break;
            default:
                openMode = IndexWriterConfig.OpenMode.CREATE;
                break;
        }
        return openMode;
    }
}
