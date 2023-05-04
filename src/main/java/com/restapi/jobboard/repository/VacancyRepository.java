package com.restapi.jobboard.repository;

import com.restapi.jobboard.model.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {

    Optional<Vacancy> findVacancyBySlug(String slug);

    @Query("SELECT v FROM vacancies v ORDER BY v.createdAt DESC, v.views DESC LIMIT 10")
    List<Vacancy> getTop10LatestMostViewedVacancies();
}
