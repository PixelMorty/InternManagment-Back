package canard.intern.post.following.backend.service;

import canard.intern.post.following.backend.Dto.TraineeDto;

import java.util.List;
import java.util.Optional;


public interface TraineeService {

    /**
     *
     * get all trainees
     * @param
     *  @return list of TraineeDto of trainees
     */
    public List<TraineeDto> getAll();

    /**
     *
     * get  a trainee by id if it exists
     * @param id id of trainee
     *  @return Optional with TraineeDto of trainee found or optional empty
     */
    Optional<TraineeDto> getById(int id);
    /**
     *
     *
     * @param  id id of trainee
     *  @return void
     */
    Boolean delete(int id);


    /**
     *
     *
     * @param  traineeDto trainee to to be created (without id)
     *  @return trainee created with its id
     * @throws RuntimeException
     */
    TraineeDto create(TraineeDto traineeDto); //ptet va pas marcher
    /**
     *
     *
     * @param  id id of trainee, traineeDto to be updated
     *  @return TraineeDto of trainee created
     */
    Optional<TraineeDto> update(int id, TraineeDto traineeDto); //ptet va pas marcher
    /**
     *
     *
     * @param  id id of trainee to be patched, traineeDto to be patched
     *  @return Optional with TraineeDto of trainee found or optional empty
     */
    Optional<TraineeDto>  patch(int id, String key, String value);



}
