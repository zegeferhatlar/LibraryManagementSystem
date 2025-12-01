import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.StudentMember;
import com.zegeferhatlar.library.service.LibraryManager;

public class Main {
    public static void main(String[] args) {

        LibraryManager manager = new LibraryManager();

        // 1) Book ve Member ekle
        Book book1 = new Book("111", "Küçük Prens", "Exupery");
        Book book2 = new Book("222", "Suç ve Ceza", "Dostoyevski");
        manager.addBook(book1);
        manager.addBook(book2);

        StudentMember student = new StudentMember(1, "Ali Öğrenci");
        manager.addMember(student);

        // 2) Title'a göre arama
        System.out.println("Title 'Küçük' ile arama sonucu:");
        manager.searchByTitle("Küçük").forEach(System.out::println);

        // 3) Book ödünç al
        System.out.println("\nÖdünç alma denemesi:");
        manager.borrowBook(1, "111");

        // 4) Aynı book'u tekrar ödünç almaya çalışma
        System.out.println("\nAynı book'u tekrar alma denemesi:");
        manager.borrowBook(1, "111");

        // 5) Book iade et (gecikme yoksa 0 gelir)
        System.out.println("\nİade denemesi:");
        double fee = manager.returnBook(1, "111");
        System.out.println("Dönen gecikme ücreti: " + fee);
    }
}
