package nl.eriksdigital.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name="`order`")
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date")
    private Instant date;

    @Transient
    private boolean isEqual = true;

    public void checkAndSetStatus(String status) {
        Optional.ofNullable(status).filter(tp -> !tp.equals(this.getStatus())).ifPresent((s) -> {
            this.setStatus(s);
            isEqual = false;
        });
    }

    public void checkAndSetTotalPrice(Double totalPrice) {
        if (totalPrice != null && !totalPrice.equals(this.getTotalPrice())) {
            this.setTotalPrice(totalPrice);
            isEqual = false;
        }
    }

    public void checkAndSetDate(Instant date) {
        Optional.ofNullable(date).filter(tp -> !tp.equals(this.getDate())).ifPresent((d) -> {
            this.setDate(d);
            isEqual = false;
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id.equals(that.id) && Objects.equals(status, that.status) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, totalPrice, date);
    }
}
