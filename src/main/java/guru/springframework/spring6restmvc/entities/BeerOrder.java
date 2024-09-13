package guru.springframework.spring6restmvc.entities;


import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.sql.SQLType;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor

@Data
@Builder
public class BeerOrder {

    public BeerOrder(UUID id, Long version, Timestamp createdDateTime, Timestamp updatedDateTime, String customerRef, Customer customer,
                     Set<BeerOrderLine> beerOrderLines, BeerOrderShipment beerOrderShipment) {
        this.id = id;
        this.version = version;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.customerRef = customerRef;
        this.setCustomer(customer);
        this.beerOrderLines = beerOrderLines;
        this.setOrderShipment(beerOrderShipment);
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column( name = "id", unique = true, nullable = false ,length = 36 , columnDefinition = "varchar(36)")
    private UUID id;
     @Version
     @Column(name = "version")
    private Long version;

     @CreationTimestamp
     @Column(name = "created_date", nullable = false , updatable = false)
     private Timestamp createdDateTime;

     @UpdateTimestamp
     @Column(name = "last_modified_date")
     private Timestamp updatedDateTime;

     public boolean isNew(){
         return id == null;
     }

     public void setOrderShipment(BeerOrderShipment orderShipment){
         this.beerOrderShipment = orderShipment;
         beerOrderShipment.setBeerOrder(this);


     }

     public void setCustomer(Customer customer){

         this.customer = customer;
         customer.getBeerOrders().add(this);
     }

     @Column(name = "customer_ref")
     private String customerRef;

     @ManyToOne(fetch = FetchType.LAZY)
     private Customer customer;

     @OneToMany(mappedBy = "BeerOrder")
     private Set<BeerOrderLine> beerOrderLines;

     @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.PERSIST)
     private BeerOrderShipment beerOrderShipment;

}
