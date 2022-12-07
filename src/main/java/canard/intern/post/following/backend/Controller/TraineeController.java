package canard.intern.post.following.backend.Controller;

import canard.intern.post.following.backend.Dto.TraineeDto;
import canard.intern.post.following.backend.enums.Gender;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.BadPaddingException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/trainees")
public class TraineeController {

    @GetMapping
    public List<TraineeDto> GetAll(){
        return List.of(
                 TraineeDto.builder()
                         .id(1)
                         .lastName("BopBopBop")
                         .firstName("BopBilly")
                         .gender(Gender.M)
                         .birthDate(LocalDate.ofYearDay(2000,29))
                         .build(),
                TraineeDto.builder()
                        .id(2)
                        .lastName("pui")
                        .gender(Gender.M)
                        .firstName("maxou")
                        .birthDate(LocalDate.ofYearDay(1850,300))
                        .build(),
                TraineeDto.builder()
                        .id(3)
                        .lastName("lesurvivant")
                        .gender(Gender.F)
                        .firstName("Ken")
                        .birthDate(LocalDate.ofYearDay(1975,143))
                        .build()
        );
    }

    @GetMapping(path="/{id}")
    public TraineeDto GetById(@PathVariable int id){
        return
                TraineeDto.builder()
                        .id(3)
                        .lastName("lesurvivant")
                        .gender(Gender.F)
                        .firstName("Ken")
                        .birthDate(LocalDate.ofYearDay(1975,143))
                        .build();
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
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public  TraineeDto upDate( @PathVariable("id") int id,@Valid @RequestBody TraineeDto traineedto){   // pour que ça mange le body en requete

        if ((Objects.nonNull(traineedto.getId())) && (traineedto.getId()!=id )){
                throw new IllegalArgumentException();
        }
        return traineedto;
    }

}
