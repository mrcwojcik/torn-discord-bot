package pl.mrwojcik.tornbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mrwojcik.tornbot.entity.Faction;

@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> {

    Faction findFirstByOrderByAge();

}
