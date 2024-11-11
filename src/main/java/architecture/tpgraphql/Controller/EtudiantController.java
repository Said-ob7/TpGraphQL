package architecture.tpgraphql.Controller;

import architecture.tpgraphql.DAO.Centre;
import architecture.tpgraphql.DAO.Etudiant;
import architecture.tpgraphql.DAO.Genre;
import architecture.tpgraphql.Repository.CentreRepository;
import architecture.tpgraphql.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class EtudiantController {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CentreRepository centreRepository;

    @QueryMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @QueryMapping
    public Optional<Etudiant> getEtudiantById(@Argument int id) {
        return etudiantRepository.findById(id);
    }

    @MutationMapping
    public Etudiant createEtudiant(
            @Argument String nom,
            @Argument String prenom,
            @Argument Genre genre,
            @Argument int centreId) {

        Etudiant etudiant = new Etudiant();
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setGenre(genre);

        Optional<Centre> centre = centreRepository.findById(centreId);
        centre.ifPresent(etudiant::setCentre);

        return etudiantRepository.save(etudiant);
    }
    @MutationMapping
    public Etudiant modifyEtudiant(
            @Argument int id,
            @Argument String nom,
            @Argument String prenom,
            @Argument Genre genre,
            @Argument int centreId) {

        Optional<Etudiant> etudiantOptional = etudiantRepository.findById(id);
        if (etudiantOptional.isPresent()) {
            Etudiant etudiant = etudiantOptional.get();
            etudiant.setNom(nom);
            etudiant.setPrenom(prenom);
            etudiant.setGenre(genre);

            Optional<Centre> centre = centreRepository.findById(centreId);
            centre.ifPresent(etudiant::setCentre);

            return etudiantRepository.save(etudiant);
        }
        throw new RuntimeException("Etudiant not found with id: " + id);
    }

    @MutationMapping
    public boolean deleteEtudiant(@Argument int id) {
        if (etudiantRepository.existsById(id)) {
            etudiantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
