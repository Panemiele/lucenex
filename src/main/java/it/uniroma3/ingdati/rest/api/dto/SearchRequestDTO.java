package it.uniroma3.ingdati.rest.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchRequestDTO {
    @Schema(description = "The list of strings used as query for searching inside Lucene indexes")
    List<String> queryStrings;
    @Schema(description = "The field used for the query", example = "TITLE")
    String fieldName;
}
