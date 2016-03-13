/* Copyright 2016 Roychoudhury, Abhishek */

package test.org.abhishek.simplicitas.adhoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abhishek.simplicitas.display.domain.DisplayBase;
import org.abhishek.simplicitas.html.enums.TagNameEnum;
import org.abhishek.simplicitas.util.common.ReflectionUtils;

/**
 * TODO
 * @author abhishek
 * @since  1.0
 */
public class FieldSpy<T> {
    public boolean[][] b = {{ false, false }, { true, true } };
    public int i = 100;
    public String name  = "Alice";
    public List<Integer> list;
    public Map<String, Integer> map;
    public T val;
    public Object[][] arr;
    public TagNameEnum tag;

    @Override
    public String toString() {
        return "name > " + name + "; list > " + list + "; map > " + map;
    }

    public static void main(String... args) {
        FieldSpy<String> spy = new FieldSpy<String>();
        spy.list = new ArrayList<Integer>();
        spy.list.add(100);
        spy.val = "Abhishek";
        spy.map = new HashMap<String, Integer>();
        spy.map.put("1", 1);
        spy.arr = new Object[][]{ { new String("Abhishek") }, { new String("Abhishek") } };
        spy.tag = TagNameEnum.TABLE;

        List<DisplayBase> displays = ReflectionUtils.extractClassFieldsForDisplay(spy);
        for (DisplayBase display : displays) {
            System.out.println(display);
        }
        System.out.println("-----------------------------------------------------------------");
        FieldSpy<String> spy2 = new FieldSpy<String>();
        System.out.println(ReflectionUtils.extractDisplayFieldsAsObject(spy2, displays));
        System.out.println(spy2.tag.getValue());
    }
}
