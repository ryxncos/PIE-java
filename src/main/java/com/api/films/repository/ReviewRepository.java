package com.api.films.repository;

import com.api.films.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFilmeId(Integer filmeId);
    void deleteByFilmeId(Integer filmeId);
}
