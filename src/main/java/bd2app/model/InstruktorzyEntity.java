package bd2app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Instruktorzy", schema = "mateuszs_projekt", catalog = "")
public class InstruktorzyEntity {
    @Id
    private int id;
    private String imię;
    private String nazwisko;
    private int telefon;
    private String pesel;
    private String hasło;
    private String email;
    private int godzStartuPracy;
    private int godzKoncaPracy;
    private Collection<InstruktorzyKategoriaEntity> instruktorzyKategoriasById;
    private Collection<RezerwacjeEntity> rezerwacjesById;

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
    @Column(name = "hasło")
    public String getHasło() {
        return hasło;
    }

    public void setHasło(String hasło) {
        this.hasło = hasło;
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
    @Column(name = "godzStartuPracy")
    public int getGodzStartuPracy() {
        return godzStartuPracy;
    }

    public void setGodzStartuPracy(int godzStartuPracy) {
        this.godzStartuPracy = godzStartuPracy;
    }

    @Basic
    @Column(name = "godzKoncaPracy")
    public int getGodzKoncaPracy() {
        return godzKoncaPracy;
    }

    public void setGodzKoncaPracy(int godzKoncaPracy) {
        this.godzKoncaPracy = godzKoncaPracy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstruktorzyEntity that = (InstruktorzyEntity) o;
        return id == that.id &&
                telefon == that.telefon &&
                pesel == that.pesel &&
                godzStartuPracy == that.godzStartuPracy &&
                godzKoncaPracy == that.godzKoncaPracy &&
                Objects.equals(imię, that.imię) &&
                Objects.equals(nazwisko, that.nazwisko) &&
                Objects.equals(hasło, that.hasło) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imię, nazwisko, telefon, pesel, hasło, email, godzStartuPracy, godzKoncaPracy);
    }

    @OneToMany(mappedBy = "instruktorzyByInstruktorzyid")
    public Collection<InstruktorzyKategoriaEntity> getInstruktorzyKategoriasById() {
        return instruktorzyKategoriasById;
    }

    public void setInstruktorzyKategoriasById(Collection<InstruktorzyKategoriaEntity> instruktorzyKategoriasById) {
        this.instruktorzyKategoriasById = instruktorzyKategoriasById;
    }

    @OneToMany(mappedBy = "instruktorzyByInstruktor")
    public Collection<RezerwacjeEntity> getRezerwacjesById() {
        return rezerwacjesById;
    }

    public void setRezerwacjesById(Collection<RezerwacjeEntity> rezerwacjesById) {
        this.rezerwacjesById = rezerwacjesById;
    }

    public boolean czyDostepny(Timestamp dataStart) {
        for (RezerwacjeEntity r : rezerwacjesById) {
            if (r.getData().compareTo(dataStart) == 0)
                return false;
        }
        return true;
    }

    public boolean czyMaPrawa(KategoriaEntity kategoriaEntity) {
        for (InstruktorzyKategoriaEntity k : instruktorzyKategoriasById)
            if (kategoriaEntity.getId() == k.getKategoriaid())
                return true;
        return false;
    }

    @Override
    public String toString() {
        return imię + " " + nazwisko;
    }
}
