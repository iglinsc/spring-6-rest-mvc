package guru.springframework.spring6restmvc.entities;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class BeerOrderLine {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false )
    private UUID id;

    @Version
    @Column(name = "version")
    private Long version;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    public boolean isNew() {
        return this.id == null;
    }

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity = 0;

    @Column(name = "quantity_allocated")
    private Integer quantityAllocated = 0;

    @ManyToOne()
    @JoinColumn(name =  "beer_order_id")
    private BeerOrder BeerOrder;

    @ManyToOne()
    @JoinColumn(name = "beer_id")
    private Beer beer;
}