package guru.springframework.spring6restmvc.model;

import guru.springframework.spring6restmvc.bootstrap.BeerStyle;
import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Builder
@Data
@Getter
@Setter
public class BeerDTO {
    private UUID id;
    private Integer version;
    @NotBlank
    @NotNull
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @NotBlank
    @NotBlank
    private String upc;


    private Integer QuantityOnDemand;

    @NotNull
    private BigDecimal price;

    private LocalDate createdDate;
    private LocalDate updatedDate;
}
