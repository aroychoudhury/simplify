/* Copyright 2016 Roychoudhury, Abhishek */

package test.org.abhishek.simplicitas.html.table;

import org.abhishek.simplicitas.html.enums.TagAttributeEnum;
import org.abhishek.simplicitas.html.table.Table;
import org.abhishek.simplicitas.html.table.Td;
import org.abhishek.simplicitas.html.table.Th;
import org.abhishek.simplicitas.html.table.Tr;

/**
 * @author abhishek
 * @since  1.0
 */
public class TestTable {
    public static void main(String[] args) {
        Table table = (new Table())
            .setAttribute(TagAttributeEnum.CLASS, "default-table")
            .setInnerContent("<caption>Something here</caption>")
            .registerChildren(
                (new Tr()).registerChildren(
                    (new Th()).setInnerContent("Name"),
                    (new Th()).setInnerContent("Experience")
                ),
                (new Tr()).registerChildren(
                    (new Td()).setInnerContent("Abhishek"),
                    (new Td()).setInnerContent("7 yrs")
                )
            );
        System.out.println(table.content());
    }
}
