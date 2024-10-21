package in.ai.myntra.repo;

import in.ai.myntra.model.Bag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BagRepository extends JpaRepository<Bag, Long> {
    Optional<Bag> findByUser_UserId(Long userId);
}
