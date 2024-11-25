package architecture.tpgraphql.DTO;

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
    private String genre;
    private int centreId;
}