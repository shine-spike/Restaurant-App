import org.junit.Test;
import static org.junit.Assert.*;

public class TestOrder {

    @Test
    public void testOrderNum(){
        Order o0 = new Order(0, 0, new MenuItem());
        Order o1 = new Order(0, 0, new MenuItem());

        assert(o0.getOrderNum() == o1.getOrderNum() - 1);
    }

    @Test
    public void testAddAddition(){
        Ingredient i = new Ingredient("i", 1, 10);
        MenuItem m = new MenuItem();
        Order o = new Order(0, 0, m);

        o.addAddition(i);

        assertTrue(o.getIngredients().contains(i));
    }

    @Test
    public void testAddSubtraction(){
        Ingredient i = new Ingredient("i", 1, 10);
        MenuItem m = new MenuItem();
        Order o = new Order(0, 0, m);

        o.addSubtraction(i);

        assertFalse(o.getIngredients().contains(i));
    }
}
