import org.junit.Test;
import static org.junit.Assert.*;

public class TestOrder {
    @Test
    public void testOrderNum(){
        Order o0 = new Order(0, 0, new MenuItem());
        Order o1 = new Order(0, 0, new MenuItem());
        assert(o0.getOrderNum() == 0 && o1.getOrderNum() == 1);
    }
}
