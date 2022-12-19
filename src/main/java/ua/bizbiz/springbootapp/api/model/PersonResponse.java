package ua.bizbiz.springbootapp.api.model;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {

    private Integer id;

    private String name;

    private String surname;

    private Integer age;
}
