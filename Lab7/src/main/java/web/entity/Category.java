package web.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable {

    @Id
    @Column(length = 10)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;
}
