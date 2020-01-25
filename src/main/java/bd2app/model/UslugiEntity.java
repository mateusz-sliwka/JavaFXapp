package bd2app.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Uslugi", schema = "mateuszs_projekt", catalog = "")
public class UslugiEntity {
    private int id;
    private String nazwa;
    private int cena;
    private int kategoriaid;
    private Collection<RezerwacjeEntity> rezerwacjesById;
    private KategoriaEntity kategoriaByKategoriaid;
    private Collection<RezerwacjeEntity> rezerwacjesById_0;
    private KategoriaEntity kategoriaByKategoriaid_0;

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
    @Column(name = "nazwa")
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Basic
    @Column(name = "cena")
    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @Basic
    @Column(name = "Kategoriaid")
    public int getKategoriaid() {
        return kategoriaid;
    }

    public void setKategoriaid(int kategoriaid) {
        this.kategoriaid = kategoriaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UslugiEntity that = (UslugiEntity) o;
        return id == that.id &&
                cena == that.cena &&
                kategoriaid == that.kategoriaid &&
                Objects.equals(nazwa, that.nazwa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nazwa, cena, kategoriaid);
    }

    @OneToMany(mappedBy = "uslugiByUsługa", cascade = CascadeType.ALL)
    public Collection<RezerwacjeEntity> getRezerwacjesById() {
        return rezerwacjesById;
    }

    public void setRezerwacjesById(Collection<RezerwacjeEntity> rezerwacjesById) {
        this.rezerwacjesById = rezerwacjesById;
    }

    @ManyToOne
    @JoinColumn(name = "Kategoriaid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public KategoriaEntity getKategoriaByKategoriaid() {
        return kategoriaByKategoriaid;
    }

    public void setKategoriaByKategoriaid(KategoriaEntity kategoriaByKategoriaid) {
        this.kategoriaByKategoriaid = kategoriaByKategoriaid;
    }

    @OneToMany(mappedBy = "uslugiByUsługa_0")
    public Collection<RezerwacjeEntity> getRezerwacjesById_0() {
        return rezerwacjesById_0;
    }

    public void setRezerwacjesById_0(Collection<RezerwacjeEntity> rezerwacjesById_0) {
        this.rezerwacjesById_0 = rezerwacjesById_0;
    }

    @ManyToOne
    @JoinColumn(name = "Kategoriaid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public KategoriaEntity getKategoriaByKategoriaid_0() {
        return kategoriaByKategoriaid_0;
    }

    public void setKategoriaByKategoriaid_0(KategoriaEntity kategoriaByKategoriaid_0) {
        this.kategoriaByKategoriaid_0 = kategoriaByKategoriaid_0;
    }

    @Override
    public String toString() {
        return nazwa + " (" + cena + "zł)";
    }
}
