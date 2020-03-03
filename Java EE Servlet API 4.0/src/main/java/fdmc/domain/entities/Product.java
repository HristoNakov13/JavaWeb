package fdmc.domain.entities;

import fdmc.domain.entities.enums.ProductType;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product  extends BaseEntity{
    private String name;
    private String description;
    private ProductType type;

    public Product() {
    }

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }
}
