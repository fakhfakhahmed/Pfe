package tn.ecnam.resources.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Reclamation")
public class Reclamation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    String id;
    String ContenuRec;
    Date DateRec;
    String EtatClient;
    String EtatRec;
    @NotNull
    @Enumerated(EnumType.STRING)
    TypeReclamation TypeRec;
}
