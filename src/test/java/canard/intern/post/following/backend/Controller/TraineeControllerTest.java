package canard.intern.post.following.backend.Controller;

import canard.intern.post.following.backend.Controller.fictures.TraineeJsonProvider;
import canard.intern.post.following.backend.Dto.TraineeDto;
import canard.intern.post.following.backend.service.TraineeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = TraineeController.class)
class TraineeControllerTest {
    @Autowired
    MockMvc mockMvc;

    final static String BASE_URL = "/api/trainees";
    final static String BASE_URL_WITH_ID =BASE_URL+ "/{id}";

    @MockBean
    TraineeService traineeService;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()) //statut
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//si c'est du JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());// pour prendre l'objet entier: $ et lui dire que c'est un tableau

    }

    @Test
    void getById_OK_found() throws Exception {
        int id = 2;
        //prepare mock response:
var traineeDto=TraineeDto.builder()
                .lastName("Bond")
                        .firstName("James")
                                .birthDate(LocalDate.of(1950,6,12))
                                        .email("james.bond007@org")
                                                .id(id)
                                                .build();
        BDDMockito.given(traineeService.getById(id))// fait en sorte que le prochain appel de trainneService.getById(i) renvoie le trainneeDTo
            .willReturn(Optional.of(traineeDto));

        // call controller with mock http client
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL_WITH_ID  , id).accept(MediaType.APPLICATION_JSON))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()) //  check que ça renvoie bien un retour de requete 200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // Check que ça envoie bien du Json
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id)) // check que l'id du Json est égal à celui d'entrée de requette
        ;

        BDDMockito.then(traineeService).should().getById(id); // vérifie que la méthode getById(id) a été appelé par traineeService
        // cette dernière est importante et présente quasi à chaque test qui fait appel au mock (imitation) de service
    }
    @Test
    void getById_KO_xmlNotAcceptable() throws Exception {
        int id = 2;
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL_WITH_ID  , id).accept(MediaType.APPLICATION_XML))
              //  .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());// vérifie que le statut est 406 (réponse quand t'envoies un type (json, xml) que le serveur reconnait pas



    }

    @Test
    void getById_KO_idNotFound() throws Exception {
        int id = 2;
        //prepare mock response:
        BDDMockito.given(traineeService.getById(id))// fait en sorte que le prochain appel de trainneService.getById(i) renvoie le trainneeDTo
                .willReturn(Optional.empty());

        // call controller with mock http client
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL_WITH_ID  , id).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;

        BDDMockito.then(traineeService).should().getById(id); // vérifie que la méthode getById(id) a été appelé par traineeService
        // cette dernière est importante et présente quasi à chaque test qui fait appel au mock (imitation) de service


    }

    @Test
    void add_OK_all_field_valid() throws Exception {

        var traineeJson = TraineeJsonProvider.traineeJsonAllFieldsValid();
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(traineeJson)
        )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        ;

    }
    @ParameterizedTest // permet de mettre des données en paramètre( prochaine ligne)// pour dire que c normal que la methode test mange un param
    @MethodSource("canard.intern.post.following.backend.Controller.fictures.TraineeJsonProvider#traineeJsonMissingNonRequiredField")
        // nom du package.nomclasse#nomMethode// cette ligne et la précente marchent un peu en binome
    //pour préciser ce qu'elle va manger(la methode dans methodSource doit renvoyer une collection (stream, tableau etc..)
    void add_OK_missing_non_required_filed(String traineeJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        ;

    }



    @ParameterizedTest
    @MethodSource("canard.intern.post.following.backend.Controller.fictures.TraineeJsonProvider#traineeJsonMissingOneRequiredField")
    void add_KO_missing_required_fields(String traineeJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson)
                )

                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
        ;
    }
    @Test
    void add_KO_invalid_birthDate() throws Exception {
        var traineeJson = TraineeJsonProvider.traineeJsonInvalidBirthDate();
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }



    @Test
    void delete_OK_trainee_deleted() throws Exception {
        // TODO NO CONTENT
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL_WITH_ID,id))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    void delete_KO() {
        // TODO on peut pas le faire  pr l'instant id not found,
        fail("Test not implemented yet");
    }


    @Test
    void upDate_KO_bad_id_good_trainee( ) throws Exception {
     var traineeJson=   TraineeJsonProvider.traineeJsonObjectRequiredFields_WithID100().toPrettyString();
        int id = 105;
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL_WITH_ID,id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Test
    void upDate_OK_good_id_good_trainee( ) throws Exception {
    var traineeJson =TraineeJsonProvider.traineeJsonObjectRequiredFields_WithID100().toPrettyString();
        int id = 100;
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL_WITH_ID,id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @ParameterizedTest
    @MethodSource("canard.intern.post.following.backend.Controller.fictures.TraineeJsonProvider#traineeJsonMissingOneRequiredField_Id100")
    void upDate_KO_good_id_bad_trainees(String traineeJson ) throws Exception {


        int id = 100;
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL_WITH_ID,id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}