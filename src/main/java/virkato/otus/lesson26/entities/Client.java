package virkato.otus.lesson26.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "fio", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "clients")
    @Fetch(FetchMode.JOIN)
    private Set<Product> products = new HashSet<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, Set<Product> products) {
        this.name = name;
        this.products = products;
    }

    @Override
    public String toString() {
        return String.format("id=%d\tname=%s", id, name);
    }
}
