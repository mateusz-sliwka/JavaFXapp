package bd2app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Rezerwacje", schema = "mateuszs_projekt", catalog = "")
public class RezerwacjeEntity {
    private int id;
    private int usługa;
    private String obecnosc;
    private Timestamp data;
    private int klient;
    private int instruktor;
    private UslugiEntity uslugiByUsługa;
    private UzytkownicyEntity uzytkownicyByKlient;
    private InstruktorzyEntity instruktorzyByInstruktor;
    private UslugiEntity uslugiByUsługa_0;
    private UzytkownicyEntity uzytkownicyByKlient_0;
    private InstruktorzyEntity instruktorzyByInstruktor_0;

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
    @Column(name = "obecnosc")
    public String getObecnosc() {
        return obecnosc;
    }

    public void setObecnosc(String obecnosc) {
        this.obecnosc = obecnosc;
    }

    @Basic
    @Column(name = "usługa")
    public int getUsługa() {
        return usługa;
    }

    public void setUsługa(int usługa) {
        this.usługa = usługa;
    }

    @Basic
    @Column(name = "data")
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Basic
    @Column(name = "klient")
    public int getKlient() {
        return klient;
    }

    public void setKlient(int klient) {
        this.klient = klient;
    }

    @Basic
    @Column(name = "instruktor")
    public int getInstruktor() {
        return instruktor;
    }

    public void setInstruktor(int instruktor) {
        this.instruktor = instruktor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RezerwacjeEntity that = (RezerwacjeEntity) o;
        return id == that.id &&
                usługa == that.usługa &&
                klient == that.klient &&
                instruktor == that.instruktor &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usługa, data, klient, instruktor);
    }

    @ManyToOne
    @JoinColumn(name = "usługa", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UslugiEntity getUslugiByUsługa() {
        return uslugiByUsługa;
    }

    public void setUslugiByUsługa(UslugiEntity uslugiByUsługa) {
        this.uslugiByUsługa = uslugiByUsługa;
    }

    @ManyToOne
    @JoinColumn(name = "klient", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UzytkownicyEntity getUzytkownicyByKlient() {
        return uzytkownicyByKlient;
    }

    public void setUzytkownicyByKlient(UzytkownicyEntity uzytkownicyByKlient) {
        this.uzytkownicyByKlient = uzytkownicyByKlient;
    }

    @ManyToOne
    @JoinColumn(name = "instruktor", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public InstruktorzyEntity getInstruktorzyByInstruktor() {
        return instruktorzyByInstruktor;
    }

    public void setInstruktorzyByInstruktor(InstruktorzyEntity instruktorzyByInstruktor) {
        this.instruktorzyByInstruktor = instruktorzyByInstruktor;
    }

    @ManyToOne
    @JoinColumn(name = "usługa", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UslugiEntity getUslugiByUsługa_0() {
        return uslugiByUsługa_0;
    }

    public void setUslugiByUsługa_0(UslugiEntity uslugiByUsługa_0) {
        this.uslugiByUsługa_0 = uslugiByUsługa_0;
    }

    @ManyToOne
    @JoinColumn(name = "klient", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UzytkownicyEntity getUzytkownicyByKlient_0() {
        return uzytkownicyByKlient_0;
    }

    public void setUzytkownicyByKlient_0(UzytkownicyEntity uzytkownicyByKlient_0) {
        this.uzytkownicyByKlient_0 = uzytkownicyByKlient_0;
    }

    @ManyToOne
    @JoinColumn(name = "instruktor", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public InstruktorzyEntity getInstruktorzyByInstruktor_0() {
        return instruktorzyByInstruktor_0;
    }

    public void setInstruktorzyByInstruktor_0(InstruktorzyEntity instruktorzyByInstruktor_0) {
        this.instruktorzyByInstruktor_0 = instruktorzyByInstruktor_0;
    }
}
