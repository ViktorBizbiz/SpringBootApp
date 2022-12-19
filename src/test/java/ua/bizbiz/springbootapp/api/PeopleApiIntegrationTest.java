package ua.bizbiz.springbootapp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class PeopleApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PeopleDataReceiver peopleDataReceiver;

    @Test
    @Sql("/database.sql")
    public void createPerson_success_test() throws Exception {

        mvc.perform(post("/people")
                        .content(asJsonString(peopleDataReceiver.getPersonDataForSuccess()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void createPerson_badRequest_test() throws Exception {

        mvc.perform(post("/people")
                        .content(asJsonString(peopleDataReceiver.getPersonDataForBadRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void getAllPeople_success_test() throws Exception {

        mvc.perform(get("/people")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void getPersonById_success_test() throws Exception {

        mvc.perform(get("/people/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void getPersonById_notFound_test() throws Exception {

        mvc.perform(get("/people/{id}", 7)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void updatePersonById_success_test() throws Exception {

        mvc.perform(put("/people/{id}", 1)
                        .content(asJsonString(peopleDataReceiver.getPersonDataForSuccess()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void updatePersonById_badRequest_test() throws Exception {

        mvc.perform(put("/people/{id}", 1)
                        .content(asJsonString(peopleDataReceiver.getPersonDataForBadRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void updatePersonById_notFound_test() throws Exception {

        mvc.perform(put("/people/{id}", 7)
                        .content(asJsonString(peopleDataReceiver.getPersonDataForSuccess()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void deletePersonById_success_test() throws Exception {

        mvc.perform(delete("/people/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Sql("/database.sql")
    public void deletePersonById_notFound_test() throws Exception {

        mvc.perform(delete("/people/{id}", 7))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
