package com.example.websocketchat.repository;

import com.example.websocketchat.entity.OutputMessage;
import org.hibernate.result.Output;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputMessageRepository extends MongoRepository<OutputMessage, String> {

    OutputMessage findTopByOrderByCurrentTimeDesc();

}
