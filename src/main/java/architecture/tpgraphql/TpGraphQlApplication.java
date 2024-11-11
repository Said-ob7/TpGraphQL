package architecture.tpgraphql;

import architecture.tpgraphql.DAO.Centre;
import architecture.tpgraphql.DAO.Etudiant;
import architecture.tpgraphql.DAO.Genre;
import architecture.tpgraphql.Repository.CentreRepository;
import architecture.tpgraphql.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class TpGraphQlApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TpGraphQlApplication.class, args);
    }

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CentreRepository centreRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Centres
        Centre centre1 = new Centre();
        centre1.setNom("Marrif");
        centre1.setAdresse("123 Marrif Street");
        centreRepository.save(centre1);

        Centre centre2 = new Centre();
        centre2.setNom("Moulay Youssef");
        centre2.setAdresse("456 Moulay Youssef Avenue");
        centreRepository.save(centre2);

        Centre centre3 = new Centre();
        centre3.setNom("Roudani");
        centre3.setAdresse("789 Roudani Boulevard");
        centreRepository.save(centre3);


        Etudiant et1 = Etudiant.builder()
                .nom("Oubid")
                .prenom("Said")
                .genre(Genre.Homme)
                .centre(centre1)
                .build();
        Etudiant et2 = Etudiant.builder()
                .nom("Hassni")
                .prenom("Oussama")
                .genre(Genre.Homme)
                .centre(centre2)
                .build();
        Etudiant et3 = Etudiant.builder()
                .nom("Hanudco")
                .prenom("Bassaasa")
                .genre(Genre.Homme)
                .centre(centre1)
                .build();
        Etudiant et4 = Etudiant.builder()
                .nom("Fatna")
                .prenom("Saadiya")
                .genre(Genre.Femme)
                .centre(centre3)
                .build();
        Etudiant et5 = Etudiant.builder()
                .nom("El Moumeni")
                .prenom("Thami")
                .genre(Genre.Homme)
                .centre(centre2)
                .build();
        Etudiant et6 = Etudiant.builder()
                .nom("Hatim")
                .prenom("Ammour")
                .genre(Genre.Homme)
                .centre(centre3)
                .build();

        etudiantRepository.saveAll(Arrays.asList(et1, et2, et3, et4, et5, et6));
    }
}
