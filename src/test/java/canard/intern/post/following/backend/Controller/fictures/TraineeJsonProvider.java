package canard.intern.post.following.backend.Controller.fictures;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.util.stream.Stream;

public class TraineeJsonProvider {



    public static ObjectNode traineeJsonObjectRequiredFields(){
        var objectMapper = new ObjectMapper();
        var trainee = objectMapper.createObjectNode();
        return trainee
                .put("lastName","Solo")
                .put("firstName","Yannt")
                .put("birthDate","1950-11-05")
                .put("email","tamer@tamer.com");
    }
    public static ObjectNode traineeJsonObjectRequiredFields_WithID100(){
        var objectMapper = new ObjectMapper();
        var trainee = objectMapper.createObjectNode();
        return trainee
                .put("lastName","Solo")
                .put("firstName","Yannt")
                .put("birthDate","1950-11-05")
                .put("email","tamer@tamer.com")
                .put("id",100)
                ;
    }
    public static String traineeJsonAllFieldsValid(){

        var trainee = traineeJsonObjectRequiredFields();
        trainee
                .put("gender","M")
                .put("phoneNumber","0627213176");

        return trainee.toPrettyString();
    }

    public static Stream<String> traineeJsonMissingNonRequiredField(){

        return Stream.of(
                traineeJsonObjectRequiredFields().toPrettyString(), // objet json en string avec que ceux necessaire
                traineeJsonObjectRequiredFields().put("gender","M").toPrettyString(),// // objet json en string avec que ceux necessaire et le genre
                traineeJsonObjectRequiredFields().put("phoneNumber","0627213176").toPrettyString()

        ) ;
    }
    public static Stream<String> traineeJsonMissingOneRequiredField(){
        var objectMapper = new ObjectMapper();
        return Stream.of(
                objectMapper.createObjectNode()
                        .put("lastName","Solo")
                        .put("firstName","Yannt")
                        .put("birthDate","1950-11-05").toPrettyString(),

                objectMapper.createObjectNode()
                        .put("lastName","Solo")
                        .put("firstName","Yannt")
                        .put("email","tamer@tamer.com")
                        .toPrettyString(),
                objectMapper.createObjectNode()
                        .put("lastName","Solo")
                        .put("birthDate","1950-11-05")
                        .put("email","tamer@tamer.com").toPrettyString(),
                objectMapper.createObjectNode()
                        .put("firstName","Yannt")
                        .put("birthDate","1950-11-05")
                        .put("email","tamer@tamer.com").toPrettyString()

        ) ;
    }

    public static Stream<String> traineeJsonMissingOneRequiredField_Id100(){
        var objectMapper = new ObjectMapper();
        return Stream.of(
                objectMapper.createObjectNode()
                        .put("lastName","Solo")
                        .put("firstName","Yannt")
                        .put("id",100)
                        .put("birthDate","1950-11-05").toPrettyString(),

                objectMapper.createObjectNode()
                        .put("lastName","Solo")
                        .put("firstName","Yannt")
                        .put("id",100)
                        .put("email","tamer@tamer.com")
                        .toPrettyString(),
                objectMapper.createObjectNode()
                        .put("lastName","Solo")
                        .put("birthDate","1950-11-05")
                        .put("id",100)
                        .put("email","tamer@tamer.com").toPrettyString(),
                objectMapper.createObjectNode()
                        .put("firstName","Yannt")
                        .put("birthDate","1950-11-05")
                        .put("id",100)
                        .put("email","tamer@tamer.com").toPrettyString()

        ) ;
    }

    public static String traineeJsonInvalidBirthDate(){
        var wrongBirthDate = LocalDate.now();
        wrongBirthDate
                .minusYears(18L)
                .plusDays(1L);
        var objectMapper = new ObjectMapper();
        var trainee = objectMapper.createObjectNode();
        return trainee
                .put("lastName","Solo")
                .put("firstName","Yannt")
                .put("birthDate",wrongBirthDate.toString())
                .put("email","tamer@tamer.com").toPrettyString();
    }
}
