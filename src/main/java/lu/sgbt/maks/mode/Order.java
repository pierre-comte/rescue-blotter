package lu.sgbt.maks.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private long id;
    private String state;
    private BigDecimal amount;
    private String description;

}
