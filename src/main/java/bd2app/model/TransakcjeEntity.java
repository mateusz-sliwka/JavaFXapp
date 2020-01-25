package bd2app.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Transakcje", schema = "mateuszs_projekt", catalog = "")
public class TransakcjeEntity {
    private int id;
    private int uzytkownik;
    private Date data;
    private Integer kwota;
    private UzytkownicyEntity uzytkownicyByUzytkownik;

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
    @Column(name = "uzytkownik")
    public int getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(int uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    @Basic
    @Column(name = "data")
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Basic
    @Column(name = "kwota")
    public Integer getKwota() {
        return kwota;
    }

    public void setKwota(Integer kwota) {
        this.kwota = kwota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransakcjeEntity that = (TransakcjeEntity) o;
        return id == that.id &&
                uzytkownik == that.uzytkownik &&
                Objects.equals(data, that.data) &&
                Objects.equals(kwota, that.kwota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uzytkownik, data, kwota);
    }

    @ManyToOne
    @JoinColumn(name = "uzytkownik", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UzytkownicyEntity getUzytkownicyByUzytkownik() {
        return uzytkownicyByUzytkownik;
    }

    public void setUzytkownicyByUzytkownik(UzytkownicyEntity uzytkownicyByUzytkownik) {
        this.uzytkownicyByUzytkownik = uzytkownicyByUzytkownik;
    }
}
