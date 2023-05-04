package com.restapi.jobboard.repository;

import com.restapi.jobboard.model.entity.Vacancy;
import com.restapi.jobboard.model.payload.response.LocationStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {

    Optional<Vacancy> findVacancyBySlug(String slug);

    //    @Query("SELECT v FROM (SELECT v FROM vacancies AS v ORDER BY v.createdAt DESC, v.views DESC LIMIT 10) ORDER BY v.views DESC")
    @Query("SELECT v FROM vacancies v ORDER BY v.createdAt DESC, v.views DESC LIMIT 10")
    List<Vacancy> getTop10LatestMostViewedVacancies();

    @Query("SELECT new com.restapi.jobboard.model.payload.response.LocationStatistics(v.location, COUNT(v)) " +
            "FROM vacancies v GROUP BY v.location ORDER BY COUNT(v) DESC")
    List<LocationStatistics> getLocationStatistics();
}
