package de.froesche.nz.darasources;

import de.froesche.nz.querybuilder.AbstractQueryBuilder;
import de.froesche.nz.querybuilder.SelectQueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DatabaseQueryWrapperTest {

    @Test
    public void testSimpleQuery(){
        Map<String, String> fromQuery = new HashMap<>();
        fromQuery.put("person",null);

        SelectQueryBuilder query1 = SelectQueryBuilder.query().select("A","B","C").from(fromQuery).where(AbstractQueryBuilder.equals("",""));

        SelectQueryBuilder query2 = SelectQueryBuilder.query().select("").from(fromQuery).where(
                AbstractQueryBuilder.and(
                        AbstractQueryBuilder.equals("",""),
                        AbstractQueryBuilder.in("", Arrays.asList("","")),
                        AbstractQueryBuilder.notEquals("","")));

        Arrays.stream(query1.getSelectBuilder().getValue()).forEach(value -> System.out.println(value));
        Assertions.assertTrue(query1.getSelectBuilder().getValue().length==3);
    }
}
