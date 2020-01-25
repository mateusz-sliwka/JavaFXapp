package bd2app.model;

import bd2app.App;
import bd2app.dao.TransakcjeDao;
import bd2app.dao.UslugiDao;
import bd2app.dao.UzytkownicyDao;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Uzytkownicy", schema = "mateuszs_projekt", catalog = "")
public class UzytkownicyEntity implements Serializable {
    @Id
    private int id;
    private String imię;
    private String nazwisko;
    private int telefon;
    private String pesel;
    private String pkk;
    private String hasło;
    private Integer eportfel;
    private String email;
    private Date dataUtworzenia;
    private int typUzytkownika;
    private Collection<RezerwacjeEntity> rezerwacjesById;
    private Collection<TransakcjeEntity> transakcjesById;

    public UzytkownicyEntity() {
        eportfel = 0;
    }

    public void setPkk(String pkk) {
        this.pkk = pkk;
    }

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
    @Column(name = "imię")
    public String getImię() {
        return imię;
    }

    public void setImię(String imię) {
        this.imię = imię;
    }

    @Basic
    @Column(name = "nazwisko")
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Basic
    @Column(name = "telefon")
    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    @Basic
    @Column(name = "pesel")
    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Basic
    @Column(name = "pkk")
    public String getPkk() {
        return pkk;
    }

    @Basic
    @Column(name = "hasło")
    public String getHasło() {
        return hasło;
    }

    public void setHasło(String hasło) {
        this.hasło = hasło;
    }

    @Basic
    @Column(name = "eportfel")
    public Integer getEportfel() {
        return eportfel;
    }

    public void setEportfel(Integer eportfel) {
        this.eportfel = eportfel;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "data_utworzenia")
    public Date getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(Date dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }

    @Basic
    @Column(name = "typ_uzytkownika")
    public int getTypUzytkownika() {
        return typUzytkownika;
    }

    public void setTypUzytkownika(int typUzytkownika) {
        this.typUzytkownika = typUzytkownika;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UzytkownicyEntity that = (UzytkownicyEntity) o;
        return id == that.id &&
                telefon == that.telefon &&
                Objects.equals(imię, that.imię) &&
                Objects.equals(nazwisko, that.nazwisko) &&
                Objects.equals(pesel, that.pesel) &&
                Objects.equals(pkk, that.pkk) &&
                Objects.equals(hasło, that.hasło) &&
                Objects.equals(eportfel, that.eportfel) &&
                Objects.equals(email, that.email) &&
                Objects.equals(dataUtworzenia, that.dataUtworzenia) &&
                Objects.equals(typUzytkownika, that.typUzytkownika);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imię, nazwisko, telefon, pesel, pkk, hasło, eportfel, email, dataUtworzenia, typUzytkownika);
    }

    @OneToMany(mappedBy = "uzytkownicyByKlient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Collection<RezerwacjeEntity> getRezerwacjesById() {
        return rezerwacjesById;
    }

    public void setRezerwacjesById(Collection<RezerwacjeEntity> rezerwacjesById) {
        this.rezerwacjesById = rezerwacjesById;
    }

    @OneToMany(mappedBy = "uzytkownicyByUzytkownik", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Collection<TransakcjeEntity> getTransakcjesById() {
        return transakcjesById;
    }

    public void setTransakcjesById(Collection<TransakcjeEntity> transakcjesById) {
        this.transakcjesById = transakcjesById;
    }

    public boolean czyDostepny(Timestamp dataStart) {
        for (RezerwacjeEntity r : rezerwacjesById) {
            if (r.getData().compareTo(dataStart) == 0)
                return false;
        }
        return true;
    }

    public void refreshPortfel() {
        int saldo = 0;
        for (TransakcjeEntity t : App.tdao.findByUserId(id)) {
            saldo += t.getKwota();
        }
        eportfel = saldo;
    }

    @Override
    public String toString() {
        return imię + " " + nazwisko + " (" + typUzytkownika + ")";
    }
}
