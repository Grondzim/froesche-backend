package de.froesche.nz.querybuilder;

import java.util.Map;


public abstract class QueryBuilder<T extends QueryBuilder> {

    protected Select selectBuilder;
    protected Insert insertBuilder;
    protected From fromBuilder;
    protected Into intoBuilder;
    protected IsCondition isConditionBuilder;


    protected QueryBuilder(){
    }

    protected abstract T buildQuery();

    public class Insert implements QueryElement<String []>{
        private String[] values;

        Insert(String... values){
            this.values = values;
        }

        public T into(String table){
            intoBuilder = new Into(table);
            return buildQuery();
        }

        @Override
        public String[] getValue() {
            return this.values;
        }
    }

    public class Select implements QueryElement<String[]>{
        private String[] columns;

        Select(String... columns){
            this.columns = columns;
        }

        public From from(Map<String,String> fromMap){
            fromBuilder = new From(fromMap);
            return fromBuilder;
        }

        @Override
        public String[] getValue() {
            return this.columns;
        }
    }

    public class From implements QueryElement<Map<String,String>>{
        private Map<String,String> from;

        From(Map<String,String> from){
            this.from=from;
        }

        public T where(IsCondition isCondition){
            isConditionBuilder = isCondition;
            return buildQuery();
        }

        @Override
        public Map<String, String> getValue() {
            return from;
        }
    }

    public class Into implements QueryElement<String>{
        private String table;

        Into(String table){
            this.table=table;
        }

        @Override
        public String getValue() {
            return table;
        }
    }

    public static EQUALS equals(String column, String value){
        return new EQUALS(column,value);
    }

    private static class EQUALS extends AbstractCondition<String,String> {
        public EQUALS(String column, String value) {
            super(column, value);
        }
    }

    public static NOTEQUALS notEquals(String column, String value){
        return new NOTEQUALS(column,value);
    }

    private static class NOTEQUALS extends AbstractCondition<String,String> {
        public NOTEQUALS(String column, String value) {
            super(column, value);
        }
    }

    public static BIGGERTHAN biggerThan(String column, String value){
        return new BIGGERTHAN(column,value);
    }

    private static class BIGGERTHAN extends AbstractCondition<String,String> {
        public BIGGERTHAN(String column, String value) {
            super(column, value);
        }
    }

    public static SMALLERTHANOREQUALS smallerThanOrEquals(String column, String value){
        return new SMALLERTHANOREQUALS(column,value);
    }

    private static class SMALLERTHANOREQUALS extends AbstractCondition<String, String> {
        public SMALLERTHANOREQUALS(String column, String value) {
            super(column, value);
        }
    }

    public static IN in(String column, Object value){
        return new IN(column,value);
    }

    private static class IN extends AbstractCondition<String, Object> {
        public IN(String column, Object value) {
            super(column, value);
        }
    }

    public static NOTIN notIn(String column, Object value){
        return new NOTIN(column,value);
    }

    private static class NOTIN extends AbstractCondition<String, Object> {
        public NOTIN(String column, Object value) {
            super(column, value);
        }
    }

    public static AND and(AbstractCondition... abstractConditions){
        return new AND(abstractConditions);
    }

    private static class AND implements IsCondition<AbstractCondition[]> {
        private AbstractCondition[] queryElements;
        public AND(AbstractCondition... abstractConditions){
            this.queryElements = abstractConditions;
        }

        @Override
        public AbstractCondition[] getValue() {
            return queryElements;
        }
    }

    public static OR or(AbstractCondition... abstractConditions){
        return new OR(abstractConditions);
    }

    private static class OR implements IsCondition<AbstractCondition[]> {
        private AbstractCondition[] queryElements;
        public OR(AbstractCondition... abstractConditions){
            this.queryElements = abstractConditions;
        }

        @Override
        public AbstractCondition[] getValue() {
            return queryElements;
        }
    }
}
