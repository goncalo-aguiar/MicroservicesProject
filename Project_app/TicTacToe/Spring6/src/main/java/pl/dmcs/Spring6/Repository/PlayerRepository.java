package pl.dmcs.Spring6.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.Spring6.Model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByUser(String user);
}