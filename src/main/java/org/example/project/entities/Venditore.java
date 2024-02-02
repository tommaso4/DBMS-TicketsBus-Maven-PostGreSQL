package org.example.project.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="venditori")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Venditore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sequenza_venditore")
    @SequenceGenerator(name="sequenza_venditore",initialValue = 1,allocationSize = 1)
    private int id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "venditore")
    private List<ProdottoAcquistato> prodottiAcquistati;

    public Venditore() {}

    public Venditore(String name) {
        this.name = name;
    }

    public List<ProdottoAcquistato> getProdottiAcquistati() {
        return prodottiAcquistati;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + '\'';
    }
}
