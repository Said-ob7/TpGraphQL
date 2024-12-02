package architecture.tpgraphql.Mapping;

import architecture.tpgraphql.DAO.Centre;
import architecture.tpgraphql.DAO.Etudiant;
import architecture.tpgraphql.DTO.EtudiantDTO;
import architecture.tpgraphql.Repository.CentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoToEtudiant {
    @Autowired
    CentreRepository centreRepository;
    public void toEtudiant(Etudiant et, EtudiantDTO dto) {
        Centre centre=
                centreRepository.findById(dto.getCentreId()).orElse(null);
        if (dto != null) {
            et.setNom(dto.getNom());
            et.setPrenom(dto.getPrenom());
            et.setGenre(dto.getGenre());
            et.setCentre(centre);
        }
    }
}
