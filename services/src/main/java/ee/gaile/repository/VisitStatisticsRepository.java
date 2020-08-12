package ee.gaile.repository;

import ee.gaile.entity.statistics.VisitStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitStatisticsRepository extends JpaRepository<VisitStatistics, Long> {
    Optional<VisitStatistics> findByUserIP(@Param("userIP") String userIP);
}
