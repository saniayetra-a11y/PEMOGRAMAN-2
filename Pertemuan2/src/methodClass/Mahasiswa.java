package methodClass;

public class Mahasiswa {
    public String nim, nama, grade;
    private float uts, uas;
    public double nilaiakhir;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public float getUts() {
        return uts;
    }

    public void setUts(float uts) {
        this.uts = uts;
    }

    public float getUas() {
        return uas;
    }

    public void setUas(float uas) {
        this.uas = uas;
    }

    public double getNilaiAkhir(){
        nilaiakhir = (uts + uas)/2;
        return nilaiakhir;
    }

    public String getGrade() {
        if(nilaiakhir < 50)
            grade = "E";
        else if (nilaiakhir < 60)
            grade = "D";
        else if (nilaiakhir < 70)
            grade = "C";
        else if (nilaiakhir < 80)
            grade = "B";
        else
            grade = "A";
        return grade;
    }
}