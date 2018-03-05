import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestOrder {

    @Test
    public void testOrderNum(){
        ArrayList<Ingredient> i = new ArrayList<>();
        i.add(new Ingredient("i", 1, 10));

        ArrayList<Ingredient> a = new ArrayList<>();
        a.add(new Ingredient("a", 1, 10));

        MenuItem m = new MenuItem(0, "mI", i, a);
        Order o0 = new Order(0, 0, m);
        Order o1 = new Order(0, 0, m);

        assert(o0.getOrderNumber() == o1.getOrderNumber() - 1);
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

    @Test
    public void printOrder(){
        ArrayList<Ingredient> i = new ArrayList<>();
        i.add(new Ingredient("i", 1, 10));

        ArrayList<Ingredient> a = new ArrayList<>();
        a.add(new Ingredient("a", 1, 10));

        MenuItem m = new MenuItem(0, "mI", i, a);
        Order o = new Order(0, 0, m);

        System.out.println(o);

    }
}
