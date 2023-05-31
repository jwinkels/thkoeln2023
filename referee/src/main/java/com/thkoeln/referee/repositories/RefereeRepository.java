package com.thkoeln.referee.repositories;

import com.thkoeln.referee.models.Referee;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface RefereeRepository extends JpaRepository<Referee, Long>{
    List<Referee> findByLastName(@Param("name") String lastName);
    List<Referee> findByLastNameContaining(@Param("name") String lastName);
    List<Referee> findByFirstNameLike(@Param("name") String firstName);
    List<Referee> findByLastNameOrFirstName(@Param("lastName") String lastName, @Param("firstName") String firstName);
    @Query("select ref from Referee ref where ref.firstName like %:firstName%")
    List<Referee> searchByFirstName(@Param("firstName") String firstName);

    @Transactional
    @Modifying
    @Query(value="call insert_ref(:firstName, :lastName)", nativeQuery=true)
    void saveReferee(@Param("firstName") String firstName, @Param("lastName") String lastName);


}
