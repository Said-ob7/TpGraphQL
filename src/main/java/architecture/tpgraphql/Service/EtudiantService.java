package architecture.tpgraphql.Service;

import architecture.tpgraphql.DAO.Centre;
import architecture.tpgraphql.DAO.Etudiant;
import architecture.tpgraphql.DAO.Genre;
import architecture.tpgraphql.DTO.EtudiantDTO;
import architecture.tpgraphql.Repository.CentreRepository;
import architecture.tpgraphql.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CentreRepository centreRepository;

    private final Sinks.Many<Etudiant> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<String> sinkDelete = Sinks.many().multicast().onBackpressureBuffer();

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

        etudiantRepository.save(etudiant);
        sink.tryEmitNext(etudiant);
        return etudiant;
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
            Etudiant et1 = etudiantRepository.findById(id).get();
            etudiantRepository.deleteById(id);
            String msg = String.format("L'etudiant %s %s du centre %s a quitter L'ecole",et1.getNom(),et1.getPrenom(),et1.getCentre().getNom());
            sinkDelete.tryEmitNext(msg);
            return true;
        }
        return false;
    }

//    private EtudiantDTO convertToDTO(Etudiant etudiant) {
//        int centreId = etudiant.getCentre() != null ? etudiant.getCentre().getId() : 0;
//        return new EtudiantDTO(etudiant.getId(), etudiant.getNom(), etudiant.getPrenom(), etudiant.getGenre().name(), centreId);
//    }
    public Flux<Etudiant> getEtudiantAddedPublisher() {
        return sink.asFlux();
    }
    public Flux<String> SubDeleteEtudiant() {
        return sinkDelete.asFlux();
    }
}