package edu.uoc.epcsd.productcatalog.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serialNumber", nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
