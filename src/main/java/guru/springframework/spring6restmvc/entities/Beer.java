package guru.springframework.spring6restmvc.entities;

import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Types;
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

    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Version
    private Integer version;

    @Size(max = 55)
    @NotNull
    @NotBlank
    private String beerName;
    @Enumerated(EnumType.STRING)
    private BeerStyle beerStyle;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;


    @Column( length = 255)
    private String email;

    private Integer quantityOnHand;
    @NotNull

    private BigDecimal price;

     @CreationTimestamp
    private LocalDateTime createdDate;
     @UpdateTimestamp
    private LocalDateTime updateDate;


}
