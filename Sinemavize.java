
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Temel Sınıf: BaseEntity
abstract class BaseEntity {
    private int id;
    private String ad;

    public BaseEntity(int id, String ad) {
        this.id = id;
        this.ad = ad;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    // Polymorphism için temel metot
    public abstract void BilgiGoster();
}

// Interface: IBiletSistemi
interface IBiletSistemi {
    void BiletSat();
    void BiletIptal();
}

// Film Sınıfı
class Film extends BaseEntity {
    private int sure;
    private String tur;
    private List<Salon> gosterimSalonlari;

    public Film(int id, String ad, int sure, String tur) {
        super(id, ad);
        this.sure = sure;
        this.tur = tur;
        this.gosterimSalonlari = new ArrayList<>();
    }

    public void SalonEkle(Salon salon) {
        gosterimSalonlari.add(salon);
    }

    @Override
    public void BilgiGoster() {
        System.out.println("Film Bilgileri:");
        System.out.println("ID: " + getId());
        System.out.println("Ad: " + getAd());
        System.out.println("Süre: " + sure + " dakika");
        System.out.println("Tür: " + tur);
        System.out.println("Gösterim Salonları:");
        for (Salon salon : gosterimSalonlari) {
            System.out.println("- " + salon.getAd());
        }
    }

    public List<Salon> getGosterimSalonlari() {
        return gosterimSalonlari;
    }
}

// Müşteri Sınıfı
class Musteri extends BaseEntity implements IBiletSistemi {
    private String telNo;
    private List<Film> satilanBiletler;

    public Musteri(int id, String ad, String telNo) {
        super(id, ad);
        this.telNo = telNo;
        this.satilanBiletler = new ArrayList<>();
    }

    public void FilmEkle(Film film) {
        satilanBiletler.add(film);
    }

    @Override
    public void BilgiGoster() {
        System.out.println("Müşteri Bilgileri:");
        System.out.println("ID: " + getId());
        System.out.println("Ad: " + getAd());
        System.out.println("Tel No: " + telNo);
        System.out.println("Satın Alınan Filmler:");
        for (Film film : satilanBiletler) {
            System.out.println("- " + film.getAd());
        }
    }

    @Override
    public void BiletSat() {
        System.out.println(getAd() + " için bilet satışı yapılıyor.");
    }

    @Override
    public void BiletIptal() {
        System.out.println(getAd() + " için bilet iptali gerçekleştiriliyor.");
    }
}

// Salon Sınıfı
class Salon extends BaseEntity {
    private int kapasite;
    private List<Film> gosterilenFilmler;
    private List<Musteri> kayitliMusteriler;

    public Salon(int id, String ad, int kapasite) {
        super(id, ad);
        this.kapasite = kapasite;
        this.gosterilenFilmler = new ArrayList<>();
        this.kayitliMusteriler = new ArrayList<>();
    }

    public void FilmEkle(Film film) {
        gosterilenFilmler.add(film);
        film.SalonEkle(this);
    }

    public void MusteriEkle(Musteri musteri) {
        if (kayitliMusteriler.size() < kapasite) {
            kayitliMusteriler.add(musteri);
        } else {
            System.out.println("Salon kapasitesi dolu!");
        }
    }

    @Override
    public void BilgiGoster() {
        System.out.println("Salon Bilgileri:");
        System.out.println("ID: " + getId());
        System.out.println("Ad: " + getAd());
        System.out.println("Kapasite: " + kapasite);
        System.out.println("Gösterilen Filmler:");
        for (Film film : gosterilenFilmler) {
            System.out.println("- " + film.getAd());
        }
        System.out.println("Kayıtlı Müşteriler:");
        for (Musteri musteri : kayitliMusteriler) {
            System.out.println("- " + musteri.getAd());
        }
    }

    public List<Musteri> getKayitliMusteriler() {
        return kayitliMusteriler;
    }
}

// Ana Uygulama Sınıfı
class Sinemasistemi {
    private static List<Musteri> musteriler = new ArrayList<>();
    private static List<Film> filmler = new ArrayList<>();
    private static List<Salon> salonlar = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            menuGoster();
            int secim = scanner.nextInt();
            scanner.nextLine();  // Boş satırı temizle

            switch (secim) {
                case 1: musteriEkle(); break;
                case 2: filmEkle(); break;
                case 3: salonEkle(); break;
                case 4: filmSalonaBagla(); break;
                case 5: musteriFilmeKaydet(); break;
                case 6: bilgileriListele(); break;
                case 0:
                    System.out.println("Sistemden çıkılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private static void menuGoster() {
        System.out.println("\n--- SİNEMA MÜŞTERİ KAYIT SİSTEMİ ---");
        System.out.println("1. Müşteri Ekle");
        System.out.println("2. Film Ekle");
        System.out.println("3. Salon Ekle");
        System.out.println("4. Film-Salon Bağla");
        System.out.println("5. Müşteriyi Filme Kaydet");
        System.out.println("6. Bilgileri Listele");
        System.out.println("0. Çıkış");
        System.out.print("Seçiminizi yapın: ");
    }

    private static void musteriEkle() {
        System.out.print("Müşteri ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Müşteri Adı: ");
        String ad = scanner.nextLine();
        System.out.print("Telefon No: ");
        String telNo = scanner.nextLine();

        Musteri musteri = new Musteri(id, ad, telNo);
        musteriler.add(musteri);
        System.out.println("Müşteri başarıyla eklendi.");
    }

    private static void filmEkle() {
        System.out.print("Film ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Film Adı: ");
        String ad = scanner.nextLine();
        System.out.print("Film Süresi (dakika): ");
        int sure = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Film Türü: ");
        String tur = scanner.nextLine();

        Film film = new Film(id, ad, sure, tur);
        filmler.add(film);
        System.out.println("Film başarıyla eklendi.");
    }

    private static void salonEkle() {
        System.out.print("Salon ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Salon Adı: ");
        String ad = scanner.nextLine();
        System.out.print("Salon Kapasitesi: ");
        int kapasite = scanner.nextInt();

        Salon salon = new Salon(id, ad, kapasite);
        salonlar.add(salon);
        System.out.println("Salon başarıyla eklendi.");
    }

    private static void filmSalonaBagla() {
        System.out.println("Bağlanacak Filmi Seçin:");
        for (int i = 0; i < filmler.size(); i++) {
            System.out.println((i + 1) + ". " + filmler.get(i).getAd());
        }
        int filmSecim = scanner.nextInt() - 1;

        System.out.println("Bağlanacak Salonu Seçin:");
        for (int i = 0; i < salonlar.size(); i++) {
            System.out.println((i + 1) + ". " + salonlar.get(i).getAd());
        }
        int salonSecim = scanner.nextInt() - 1;

        salonlar.get(salonSecim).FilmEkle(filmler.get(filmSecim));
        System.out.println("Film salona başarıyla bağlandı.");
    }

    private static void musteriFilmeKaydet() {
        System.out.println("Kayıt Yapılacak Müşteriyi Seçin:");
        for (int i = 0; i < musteriler.size(); i++) {
            System.out.println((i + 1) + ". " + musteriler.get(i).getAd());
        }
        int musteriSecim = scanner.nextInt() - 1;

        System.out.println("Kayıt Yapılacak Filmi Seçin:");
        for (int i = 0; i < filmler.size(); i++) {
            System.out.println((i + 1) + ". " + filmler.get(i).getAd());
        }
        int filmSecim = scanner.nextInt() - 1;

        Film secilenFilm = filmler.get(filmSecim);
        Musteri secilenMusteri = musteriler.get(musteriSecim);

        // Filmin gösterildiği bir salonu bulup müşteriyi o salona kaydet
        if (!secilenFilm.getGosterimSalonlari().isEmpty()) {
            Salon salon = secilenFilm.getGosterimSalonlari().get(0);
            salon.MusteriEkle(secilenMusteri);
            secilenMusteri.FilmEkle(secilenFilm);
            System.out.println("Müşteri filme başarıyla kaydedildi.");
        } else {
            System.out.println("Film herhangi bir salona bağlanmamış!");
        }
    }

    private static void bilgileriListele() {
        System.out.println("\n--- TÜM BİLGİLER ---");

        System.out.println("\nMÜŞTERİLER:");
        for (Musteri musteri : musteriler) {
            musteri.BilgiGoster();
            System.out.println("---");
        }

        System.out.println("\nFİLMLER:");
        for (Film film : filmler) {
            film.BilgiGoster();
            System.out.println("---");
        }

        System.out.println("\nSALONLAR:");
        for (Salon salon : salonlar) {
            salon.BilgiGoster();
            System.out.println("---");
        }
    }
}