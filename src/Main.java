import com.zegeferhatlar.library.model.*;

public class Main {
    public static void main(String[] args) {

        Book book = new Book("123", "Küçük Prens", "Exupery");
        StudentMember student = new StudentMember(10, "Ali Öğrenci");

        Loan loan = new Loan(book, student);

        System.out.println("Ödünç alınan tarih: " + loan.getLoanDate());
        System.out.println("Gecikme günleri: " + loan.calculateOverdueDays());
        System.out.println("Gecikme ücreti: " + loan.calculateLateFee());
    }
}
