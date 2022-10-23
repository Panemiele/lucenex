package it.uniroma3.ingdati.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentVO {
    Integer id;
    String title;
    Float rankWeight;
}
