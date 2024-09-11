package guru.springframework.spring6restmvc.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.sql.SQLType;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeerOrder {


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

     @Column(name = "customer_ref")
     private String customerRef;

     @ManyToOne(fetch = FetchType.LAZY)
     private Customer customer;

}
