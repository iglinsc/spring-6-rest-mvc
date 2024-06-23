package guru.springframework.spring6restmvc.entities;

import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36 ,columnDefinition = "varchar" , updatable = false , nullable = false )
    @Id
    private UUID id;

    @Version
    private Integer version;

    @Size(max = 55)
    @NotNull
    @NotBlank
    private String beerName;
    private BeerStyle beerStyle;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;
    @NotNull

    private Integer quantityOnHand;
    @NotNull

    private BigDecimal price;
    @NotNull

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


}
