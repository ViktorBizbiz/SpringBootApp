package ua.bizbiz.springbootapp.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {

    private Integer id;

    private String name;

    private String surname;

    private Integer age;
}
