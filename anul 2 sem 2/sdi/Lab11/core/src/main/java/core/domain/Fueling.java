package core.domain;

import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import javax.persistence.*;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "fueling")
@IdClass(CompositeKey.class)
public class Fueling{
    @Id
    @Column(name = "carid")
    private Integer articleId;

    @Id
    @Column(name = "gaasstationid")
    private Integer journalistId;
}