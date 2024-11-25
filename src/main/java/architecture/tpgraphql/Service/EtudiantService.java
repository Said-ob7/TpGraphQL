package architecture.tpgraphql.Service;

import architecture.tpgraphql.DAO.Centre;
import architecture.tpgraphql.DAO.Etudiant;
import architecture.tpgraphql.DAO.Genre;
import architecture.tpgraphql.DTO.EtudiantDTO;
import architecture.tpgraphql.Repository.CentreRepository;
import architecture.tpgraphql.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CentreRepository centreRepository;

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Optional<Etudiant> getEtudiantById(int id) {
        return etudiantRepository.findById(id);
    }

    public Etudiant createEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(etudiantDTO.getNom());
        etudiant.setPrenom(etudiantDTO.getPrenom());
        etudiant.setGenre(Genre.valueOf(etudiantDTO.getGenre()));

        Optional<Centre> centre = centreRepository.findById(etudiantDTO.getCentreId());
        centre.ifPresent(etudiant::setCentre);

        return etudiantRepository.save(etudiant);
    }

    public Etudiant modifyEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = etudiantRepository.findById(etudiantDTO.getId())
                .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + etudiantDTO.getId()));

        etudiant.setNom(etudiantDTO.getNom());
        etudiant.setPrenom(etudiantDTO.getPrenom());
        etudiant.setGenre(Genre.valueOf(etudiantDTO.getGenre()));

        Optional<Centre> centre = centreRepository.findById(etudiantDTO.getCentreId());
        centre.ifPresent(etudiant::setCentre);

        return etudiantRepository.save(etudiant);
    }

    public boolean deleteEtudiant(int id) {
        if (etudiantRepository.existsById(id)) {
            etudiantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EtudiantDTO convertToDTO(Etudiant etudiant) {
        int centreId = etudiant.getCentre() != null ? etudiant.getCentre().getId() : 0;
        return new EtudiantDTO(etudiant.getId(), etudiant.getNom(), etudiant.getPrenom(), etudiant.getGenre().name(), centreId);
    }
}