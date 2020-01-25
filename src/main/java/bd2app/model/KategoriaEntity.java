package bd2app.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Kategoria", schema = "mateuszs_projekt", catalog = "")
public class KategoriaEntity {
    private int id;
    private String oznaczenie;
    private Collection<InstruktorzyKategoriaEntity> instruktorzyKategoriasById;
    private Collection<UslugiEntity> uslugisById;
    private Collection<InstruktorzyKategoriaEntity> instruktorzyKategoriasById_0;
    private Collection<UslugiEntity> uslugisById_0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "oznaczenie")
    public String getOznaczenie() {
        return oznaczenie;
    }

    public void setOznaczenie(String oznaczenie) {
        this.oznaczenie = oznaczenie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KategoriaEntity that = (KategoriaEntity) o;
        return id == that.id &&
                Objects.equals(oznaczenie, that.oznaczenie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oznaczenie);
    }

    @OneToMany(mappedBy = "kategoriaByKategoriaid")
    public Collection<InstruktorzyKategoriaEntity> getInstruktorzyKategoriasById() {
        return instruktorzyKategoriasById;
    }

    public void setInstruktorzyKategoriasById(Collection<InstruktorzyKategoriaEntity> instruktorzyKategoriasById) {
        this.instruktorzyKategoriasById = instruktorzyKategoriasById;
    }

    @OneToMany(mappedBy = "kategoriaByKategoriaid")
    public Collection<UslugiEntity> getUslugisById() {
        return uslugisById;
    }

    public void setUslugisById(Collection<UslugiEntity> uslugisById) {
        this.uslugisById = uslugisById;
    }

    @OneToMany(mappedBy = "kategoriaByKategoriaid_0")
    public Collection<InstruktorzyKategoriaEntity> getInstruktorzyKategoriasById_0() {
        return instruktorzyKategoriasById_0;
    }

    public void setInstruktorzyKategoriasById_0(Collection<InstruktorzyKategoriaEntity> instruktorzyKategoriasById_0) {
        this.instruktorzyKategoriasById_0 = instruktorzyKategoriasById_0;
    }

    @OneToMany(mappedBy = "kategoriaByKategoriaid_0", cascade = CascadeType.ALL)
    public Collection<UslugiEntity> getUslugisById_0() {
        return uslugisById_0;
    }

    public void setUslugisById_0(Collection<UslugiEntity> uslugisById_0) {
        this.uslugisById_0 = uslugisById_0;
    }

    @Override
    public String toString() {
        return oznaczenie;
    }
}
