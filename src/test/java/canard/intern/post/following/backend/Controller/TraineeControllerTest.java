package canard.intern.post.following.backend.Controller;

import canard.intern.post.following.backend.Controller.fictures.TraineeJsonProvider;
import canard.intern.post.following.backend.Dto.TraineeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = TraineeController.class)
class TraineeControllerTest {
    @Autowired
    MockMvc mockMvc;
    final static String BASE_URL = "/api/trainees";
    final static String BASE_URL_WITH_ID =BASE_URL+ "/{id}";
    @Test
    void getAll() {
        // TODO
        fail("Test not implemented yet");
    }

    @Test
    void getById() throws Exception {
        int id = 3;
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL_WITH_ID  , id).accept(MediaType.APPLICATION_JSON))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()) //  check que ça renvoie bien un retour de requete 200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // Check que ça envoie bien du Json
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id)) // check que l'id du Json est égal à celui d'entrée de requete
        ;
    }
    @Test
    void getById_KO_xmlNotAcceptable() throws Exception {
        int id = 2;
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL_WITH_ID  , id).accept(MediaType.APPLICATION_XML))
              //  .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());// vérifie que le statut est 406 (réponse quand t'envoies un type (json, xml) que le serveur reconnait pas
    }

    @Test
    void getById_KO_idNotFound(){
        // TODO
        fail("Test not implemented yet");
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



    @Test
    void add_KO_missing_required_fields() {
        // lastnam firstname email birthdate
        fail("Test not implemented yet");
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
    void delete() {
        // TODO
        fail("Test not implemented yet");
    }

    @Test
    void upDate() {
        // TODO
        fail("Test not implemented yet");
    }
}