import com.cron.CronExpressionParser;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CronExpressionParserTest {


    @Test
    @DisplayName("Test Case With Simple Cron Expression")
    public void testSimpleCronExpression() throws Exception {
        Map<String, List<String>> response = CronExpressionParser.evaluateCronExpression("*/15 0 1,jan-march * 1-5 /usr/bin/find");
        assertEquals(String.join(" and ", response.get("minute")), "0 and 15 and 30 and 45");
        assertEquals(String.join(" and ", response.get("hour")), "0");
        assertEquals(String.join(" and ", response.get("dayofmonth")), "1");
        assertEquals(String.join(" and ", response.get("month")), "1 and 2 and 3 and 4 and 5 and 6 and 7 and 8 and 9 and 10 and 11 and 12");
        assertEquals(String.join(" and ", response.get("dayofweek")), "1 and 2 and 3 and 4 and 5");

        assertEquals(String.join(" and ", response.get("command")), "/usr/bin/find");
    }

    @Test
    @DisplayName("Test Cron Expression With Complex Lists")
    public void testCronExpressionWithComplexLists() throws Exception{
        Map<String, List<String>> response = CronExpressionParser.evaluateCronExpression("*/15 0 1-5,1-15 * 1-5 /usr/bin/find");
        assertEquals(String.join(" and ", response.get("minute")), "0 and 15 and 30 and 45");
        assertEquals(String.join(" and ", response.get("hour")), "0");
        assertEquals(String.join(" and ", response.get("dayofmonth")), "1 and 2 and 3 and 4 and 5 and 1 and 2 and 3 and 4 and 5 and 6 and 7 and 8 and 9 and 10 and 11 and 12 and 13 and 14 and 15");
        assertEquals(String.join(" and ", response.get("month")), "1 and 2 and 3 and 4 and 5 and 6 and 7 and 8 and 9 and 10 and 11 and 12");
        assertEquals(String.join(" and ", response.get("dayofweek")), "1 and 2 and 3 and 4 and 5");
    }

    @Test(expected = Exception.class)
    @DisplayName("Test Cron Expression With Wrong Argument")
    public void testCronExpressionWithWrongArgument() throws Exception  {
        CronExpressionParser.evaluateCronExpression(null);
    }

    @Test(expected = Exception.class)
    @DisplayName("Test Cron Expression With Argumentas Empty String")
    public void testCronExpressionWithArgumentasEmptyString() throws Exception  {
        CronExpressionParser.evaluateCronExpression("");
    }

}
