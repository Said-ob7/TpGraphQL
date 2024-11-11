package architecture.tpgraphql.Repository;

import architecture.tpgraphql.DAO.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {
}
