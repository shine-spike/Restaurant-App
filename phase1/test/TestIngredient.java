import org.junit.Test;
import static org.junit.Assert.*;

public class TestIngredient {
    @Test
    public void TestIngredientConstructor(){
        Ingredient i = new Ingredient("i", 0, 1);

        assertTrue(i.getName().equals("i"));
        assertTrue(i.getThreshold() == 0);
        assertTrue(i.getQuantity() == 1);
    }

    @Test
    public void TestIngredientEquals(){
        Ingredient i1 = new Ingredient("i", 0, 1);
        Ingredient i2 = i1;
        Ingredient i3 = new Ingredient("j", 0, 1);

        assertTrue(i1.equals(i1) && i2.equals(i2) && i3.equals(i3));
        assertTrue(i1.equals(i2));
        assertFalse(i1.equals(i3));
    }
}
