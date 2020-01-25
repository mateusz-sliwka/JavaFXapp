package bd2app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Instruktorzy_Kategoria", schema = "mateuszs_projekt", catalog = "")
public class InstruktorzyKategoriaEntity implements Serializable {
    private int instruktorzyid;
    private int kategoriaid;
    private InstruktorzyEntity instruktorzyByInstruktorzyid;
    private KategoriaEntity kategoriaByKategoriaid;
    private InstruktorzyEntity instruktorzyByInstruktorzyid_0;
    private KategoriaEntity kategoriaByKategoriaid_0;

    @Id
    @Column(name = "Instruktorzyid")
    public int getInstruktorzyid() {
        return instruktorzyid;
    }

    public void setInstruktorzyid(int instruktorzyid) {
        this.instruktorzyid = instruktorzyid;
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
        InstruktorzyKategoriaEntity that = (InstruktorzyKategoriaEntity) o;
        return instruktorzyid == that.instruktorzyid &&
                kategoriaid == that.kategoriaid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instruktorzyid, kategoriaid);
    }

    @ManyToOne
    @JoinColumn(name = "Instruktorzyid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public InstruktorzyEntity getInstruktorzyByInstruktorzyid() {
        return instruktorzyByInstruktorzyid;
    }

    public void setInstruktorzyByInstruktorzyid(InstruktorzyEntity instruktorzyByInstruktorzyid) {
        this.instruktorzyByInstruktorzyid = instruktorzyByInstruktorzyid;
    }

    @ManyToOne
    @JoinColumn(name = "Kategoriaid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public KategoriaEntity getKategoriaByKategoriaid() {
        return kategoriaByKategoriaid;
    }

    public void setKategoriaByKategoriaid(KategoriaEntity kategoriaByKategoriaid) {
        this.kategoriaByKategoriaid = kategoriaByKategoriaid;
    }

    @ManyToOne
    @JoinColumn(name = "Instruktorzyid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public InstruktorzyEntity getInstruktorzyByInstruktorzyid_0() {
        return instruktorzyByInstruktorzyid_0;
    }

    public void setInstruktorzyByInstruktorzyid_0(InstruktorzyEntity instruktorzyByInstruktorzyid_0) {
        this.instruktorzyByInstruktorzyid_0 = instruktorzyByInstruktorzyid_0;
    }

    @ManyToOne
    @JoinColumn(name = "Kategoriaid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public KategoriaEntity getKategoriaByKategoriaid_0() {
        return kategoriaByKategoriaid_0;
    }

    public void setKategoriaByKategoriaid_0(KategoriaEntity kategoriaByKategoriaid_0) {
        this.kategoriaByKategoriaid_0 = kategoriaByKategoriaid_0;
    }
}
