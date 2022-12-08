package canard.intern.post.following.backend.Controller;

import canard.intern.post.following.backend.Dto.TraineeDto;
import canard.intern.post.following.backend.enums.Gender;
import canard.intern.post.following.backend.service.TraineeService;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.BadPaddingException;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/trainees")
public class TraineeController {

    @Autowired // spring stp file moi un trainee service
    private TraineeService traineeService;
    @GetMapping
    public List<TraineeDto> getAll(){
//        return List.of(
//                 TraineeDto.builder()
//                         .id(1)
//                         .lastName("BopBopBop")
//                         .firstName("BopBilly")
//                         .gender(Gender.M)
//                         .birthDate(LocalDate.ofYearDay(2000,29))
//                         .build(),
//                TraineeDto.builder()
//                        .id(2)
//                        .lastName("pui")
//                        .gender(Gender.M)
//                        .firstName("maxou")
//                        .birthDate(LocalDate.ofYearDay(1850,300))
//                        .build(),
//                TraineeDto.builder()
//                        .id(3)
//                        .lastName("lesurvivant")
//                        .gender(Gender.F)
//                        .firstName("Ken")
//                        .birthDate(LocalDate.ofYearDay(1975,143))
//                        .build()
//        );
        return traineeService.getAll();
    }

    @GetMapping(path="/{id}")
    public TraineeDto getById(@PathVariable int id){
        var optTraineeDto= traineeService.getById(id);
        if (optTraineeDto.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"id pas trouvé"); //MessageFormat.format("no id {0,int} found",id));
        return optTraineeDto.get();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  TraineeDto add( @Valid @RequestBody TraineeDto traineedto){   // pour que ça mange le body en requete

        traineedto.setId(10);
        return traineedto;
    }


    @DeleteMapping(path ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Integer delete(@PathVariable("id") int id){
        return id;
    }


//    @PutMapping("/{id}")
//    public TraineeDto putById(@PathVariable("id")int id, @Valid @RequestBody TraineeDto traineeDto ){
//        if (Objects.nonNull(traineeDto.getId()) && (traineeDto.getId() !=id)){
//            throw new IllegalArgumentException();
//        }
//        return  traineeDto;
//    }

//    @PutMapping( headers = {"key_id=id"})
//    public TraineeDto update(
//            @PathVariable("id") int id,
//            @Valid @RequestBody TraineeDto traineeDto
//    ){
//        if (Objects.nonNull(traineeDto.getId()) && (traineeDto.getId() != id)) {
//            throw new IllegalArgumentException();
//        }
//        return traineeDto;
//    }

    @PutMapping(path ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  TraineeDto upDate( @PathVariable("id") int id,@Valid @RequestBody TraineeDto traineedto){   // pour que ça mange le body en requete

        if ((Objects.nonNull(traineedto.getId())) && (traineedto.getId()!=id )){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  String.format("Met pas deux id différents entre path(%d) et celui du stagiaire(%d) tocard",id,traineedto.getId()));
        }
        return traineedto;
    }

}
