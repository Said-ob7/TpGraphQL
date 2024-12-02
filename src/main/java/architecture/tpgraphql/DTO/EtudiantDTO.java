package architecture.tpgraphql.DTO;

import architecture.tpgraphql.DAO.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantDTO {
    private int id;
    private String nom;
    private String prenom;
    private Genre genre;
    private int centreId;
}