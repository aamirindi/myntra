package in.ai.myntra.repo;

import in.ai.myntra.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    Optional<WishList> findByUserUserId(Long userId);
}
