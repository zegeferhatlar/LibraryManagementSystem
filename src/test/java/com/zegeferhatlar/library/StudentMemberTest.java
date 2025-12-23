import com.zegeferhatlar.library.model.StudentMember;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentMemberTest {

    @Test
    void calculateFee_shouldReturnZero_whenNoOverdueDays() {
        StudentMember member = new StudentMember(1, "Ali");

        double fee = member.calculateFee(0);

        assertEquals(0.0, fee);
    }

    @Test
    void calculateFee_shouldCalculateFeeBasedOnDailyRate() {
        StudentMember member = new StudentMember(1, "Ali");

        double fee = member.calculateFee(4);

        assertEquals(2.0, fee, 0.0001); // 4 * 0.5
    }
}
