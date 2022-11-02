package it.uniroma3.ingdati.rest.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentDTO {
    @Schema(description = "The document unique identifier")
    Integer id;
    @Schema(description = "The document title")
    String title;
    @Schema(description = "The rank assigned to the document by Lucene")
    Float rankWeight;
}
