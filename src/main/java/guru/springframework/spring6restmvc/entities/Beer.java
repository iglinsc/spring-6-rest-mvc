package guru.springframework.spring6restmvc.entities;


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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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


    @Column( length = 100)
    private String email;

    private Integer quantityOnHand;
    @NotNull

    private BigDecimal price;

     @CreationTimestamp
    private LocalDateTime createdDate;
     @UpdateTimestamp
    private LocalDateTime updateDate;

     @OneToMany(mappedBy = "beer")
     private Set<BeerOrderLine> beerOrderLines;

     public void addCategory(Category category) {
         this.categories.add(category);
         category.getBeers().add(this);
     }

     public void removeCategory(Category category) {

         this.categories.remove(category);
         category.getBeers().remove(this);
     }

     @Builder.Default
     @ManyToMany(fetch = FetchType.EAGER)
     @JoinTable( name = "beer_category",  joinColumns = @JoinColumn(name = "beer_id") , inverseJoinColumns = @JoinColumn(name = "category_id"))
     private Set<Category> categories = new HashSet<>();

}
