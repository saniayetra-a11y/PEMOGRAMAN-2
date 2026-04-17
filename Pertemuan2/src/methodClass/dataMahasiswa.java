package methodClass;

import java.util.Scanner;

public class dataMahasiswa {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Mahasiswa[] mhs = new Mahasiswa[0];
        int jml, x, pil;
        float uts, uas;

        do {
            System.out.println("\nPILIH MENU");
            System.out.println("======================");
            System.out.println("1. Input Data");
            System.out.println("2. Tampil Data");
            System.out.println("3. Keluar Program");
            System.out.println("======================");

            System.out.print("Pilih Menu: ");
            pil = input.nextInt();

            switch (pil) {

                case 1 -> {
                    System.out.print("Jumlah Data: ");
                    jml = input.nextInt();
                    mhs = new Mahasiswa[jml];

                    for (x = 0; x < jml; x++) {
                        mhs[x] = new Mahasiswa();

                        System.out.println("\nData ke-" + (x + 1));

                        System.out.print("NIM: ");
                        mhs[x].setNim(input.next());

                        System.out.print("Nama: ");
                        mhs[x].setNama(input.next());

                        System.out.print("Nilai UTS: ");
                        uts = input.nextFloat();

                        System.out.print("Nilai UAS: ");
                        uas = input.nextFloat();

                        mhs[x].setUts(uts);
                        mhs[x].setUas(uas);
                    }
                }

                case 2 -> {
                    System.out.println("\n=== TAMPIL DATA ===");

                    for (x = 0; x < mhs.length; x++) {
                        System.out.println("\nData ke-" + (x + 1));
                        System.out.println("NIM: " + mhs[x].getNim());
                        System.out.println("Nama: " + mhs[x].getNama());
                        System.out.println("Nilai UTS: " + mhs[x].getUts());
                        System.out.println("Nilai UAS: " + mhs[x].getUas());
                        System.out.println("Nilai Akhir: " + mhs[x].getNilaiAkhir());
                        System.out.println("Grade: " + mhs[x].getGrade());
                    }
                }

                case 3 -> System.out.println("Program selesai");

                default -> System.out.println("Pilihan tidak valid!");
            }

        } while (pil != 3);
    }
}