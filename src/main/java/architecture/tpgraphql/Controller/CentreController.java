package architecture.tpgraphql.Controller;

import architecture.tpgraphql.DAO.Centre;
import architecture.tpgraphql.Repository.CentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class CentreController {
    @Autowired
    private CentreRepository centreRepository;

    @QueryMapping
    public List<Centre> getAllCentres() {
        return centreRepository.findAll();
    }

    @QueryMapping
    public Optional<Centre> getCentreById(@Argument int id) {
        return centreRepository.findById(id);
    }

    @MutationMapping
    public Centre createCentre(@Argument String nom, @Argument String adresse) {
        Centre centre = new Centre();
        centre.setNom(nom);
        centre.setAdresse(adresse);
        return centreRepository.save(centre);
    }

    @MutationMapping
    public Centre modifyCentre(@Argument int id, @Argument String nom, @Argument String adresse) {
        Optional<Centre> centreOptional = centreRepository.findById(id);
        if (centreOptional.isPresent()) {
            Centre centre = centreOptional.get();
            centre.setNom(nom);
            centre.setAdresse(adresse);
            return centreRepository.save(centre);
        }
        throw new RuntimeException("Centre not found with id: " + id);
    }

    @MutationMapping
    public boolean deleteCentre(@Argument int id) {
        if (centreRepository.existsById(id)) {
            centreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
