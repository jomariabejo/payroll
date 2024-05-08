package deductionTest;

import com.jomariabejo.motorph.service.DeductionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class computePhilhealthContributionTestWithMonthlyBasicSalary30000 {
    private BigDecimal monthlyBasicSalary;
    private BigDecimal expectedResult;
    private DeductionService deductionService;

    @BeforeEach
    void setUp() {
        monthlyBasicSalary = new BigDecimal("30000.00");
        expectedResult = new BigDecimal("900.00").divide(BigDecimal.valueOf(2)); // Divide by 2 as the payments are equally shared between the employee and employer.
        deductionService = new DeductionService();
    }

    @Test
    void philhealthComputationShouldBe450() {
        BigDecimal actualResult = deductionService.deductPhilhealth(monthlyBasicSalary);
        assertEquals(expectedResult, actualResult, "The PhilHealth computation should be within the expected range");
    }
}