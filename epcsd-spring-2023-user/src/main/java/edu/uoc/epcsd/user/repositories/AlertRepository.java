package edu.uoc.epcsd.user.repositories;

import edu.uoc.epcsd.user.entities.Alert;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlertRepository extends JpaRepository<Alert, Long> {


    @Query("SELECT a FROM Alert a WHERE a.user.id = :userId AND a.from >= :fromDate AND a.to <= :toDate")
    List<Alert> findByUserIdAndDateRange(@Param("userId") Long userId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT a FROM Alert a WHERE a.brand = :product AND a.from <= :date AND a.to >= :date")
    List<Alert> findByProductAndDate(@Param("product") String product, @Param("date") Date date);

}
