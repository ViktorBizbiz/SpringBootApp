package ua.bizbiz.springbootapp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ua.bizbiz.springbootapp.api.model.PersonResponse;
import ua.bizbiz.springbootapp.data.PeopleDataReceiver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.bizbiz.springbootapp.utils.JsonUtils.asJsonString;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PeopleApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PeopleDataReceiver peopleDataReceiver;

    @Test
    public void createPerson_success_test() throws Exception {

        var requestData = peopleDataReceiver.getPersonData();
        var expectedResponse = peopleDataReceiver.getPersonResponse();

        var result = mvc.perform(post("/people")
                        .content(asJsonString(requestData))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var response = new ObjectMapper().readValue(content, PersonResponse.class);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void createPerson_badRequest_test() throws Exception {

        var requestData = peopleDataReceiver.getPersonData();
        requestData.setBirthdate(LocalDate.parse("4000-11-11"));

        mvc.perform(post("/people")
                        .content(asJsonString(requestData))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Sql("/init-person-table.sql")
    public void getAllPeople_success_test() throws Exception {

        var expectedResponse = new ArrayList<PersonResponse>();
        expectedResponse.add(peopleDataReceiver.getPersonResponse());

        var result = mvc.perform(get("/people")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var json = result.getResponse().getContentAsString();
        var response = Arrays.asList(new ObjectMapper().readValue(json, PersonResponse[].class));

        assertEquals(expectedResponse, response);
    }

    @Test
    @Sql("/init-person-table.sql")
    public void getPersonById_success_test() throws Exception {

        var expectedResponse = peopleDataReceiver.getPersonResponse();

        var result = mvc.perform(get("/people/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var response = new ObjectMapper().readValue(content, PersonResponse.class);

        assertEquals(expectedResponse, response);
    }

    @Test
    @Sql("/init-person-table.sql")
    public void getPersonById_notFound_test() throws Exception {

        mvc.perform(get("/people/{id}", 7)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Sql("/init-person-table.sql")
    public void updatePersonById_success_test() throws Exception {

        var requestData = peopleDataReceiver.getPersonData();
        var expectedResponse = peopleDataReceiver.getPersonResponse();

        var result = mvc.perform(put("/people/{id}", 1)
                        .content(asJsonString(requestData))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var response = new ObjectMapper().readValue(content, PersonResponse.class);

        assertEquals(expectedResponse, response);
    }

    @Test
    @Sql("/init-person-table.sql")
    public void updatePersonById_badRequest_test() throws Exception {

        var requestData = peopleDataReceiver.getPersonData();
        requestData.setBirthdate(LocalDate.parse("4000-11-11"));

        mvc.perform(put("/people/{id}", 1)
                        .content(asJsonString(requestData))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Sql("/init-person-table.sql")
    public void updatePersonById_notFound_test() throws Exception {

        var requestData = peopleDataReceiver.getPersonData();

        mvc.perform(put("/people/{id}", 7)
                        .content(asJsonString(requestData))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Sql("/init-person-table.sql")
    public void deletePersonById_success_test() throws Exception {

        mvc.perform(delete("/people/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Sql("/init-person-table.sql")
    public void deletePersonById_notFound_test() throws Exception {

        mvc.perform(delete("/people/{id}", 7))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
