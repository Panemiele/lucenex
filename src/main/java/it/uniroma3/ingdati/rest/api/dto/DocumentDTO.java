package it.uniroma3.ingdati.rest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentDTO {
    Integer id;
    String title;
    Float rankWeight;
}
