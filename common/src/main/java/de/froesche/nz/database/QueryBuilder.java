package de.froesche.nz.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class QueryBuilder {

    public static final String SELECT_ALL = "*";


    private Select querySelect;
    private From queryFrom;
    private List<Condition> queryCondition = new ArrayList<>();
    private List<Where> queryWhere = new ArrayList<>();
    private List<In> queryIn  = new ArrayList<>();

    private QueryBuilder(){

    }

    private QueryBuilder(Select select, From from, List<Where> where, List<In> in){
        querySelect = select;
        queryFrom = from;
        queryWhere = where;
        queryIn = in;
    }

    private QueryBuilder(Select select, From from, List<Condition> condition){
        querySelect = select;
        queryFrom = from;
        queryCondition = condition;
    }

    private QueryBuilder(Select select, From from){
        querySelect = select;
        queryFrom = from;
    }

    public static QueryBuilder createQuery(){
        return new QueryBuilder();
    }

    public Select select(String... select) {
        querySelect = new Select(select);
        return getQuerySelect();
    }

    public Select getQuerySelect() {
        return querySelect;
    }

    public From getQueryFrom() {
        return queryFrom;
    }

    public List<Condition> getQueryCondition() {
        return queryCondition;
    }

    public class Select{
        private String[] columns;

        Select(String... columns){
            this.columns = columns;
            querySelect = this;
        }

        public From from(String... from){
            queryFrom = new From(from);
            return getQueryFrom();
        }
    }

    public class From{
        private String[] from;

        From(String[] from){
            this.from=from;
        }

        public Condition where(String column, String whereClausal){
            Condition where = new Condition(column,whereClausal);
            getQueryCondition().add(where);
            return where;
        }

        public Condition in(String column, QueryBuilder inClausal){
            Condition in = new Condition(column, inClausal);
            getQueryCondition().add(in);
            return in;
        }

        public Condition in(String column, List<String> inClausal){
            Condition in = new Condition(column, inClausal);
            getQueryCondition().add(in);
            return in;
        }

        public QueryBuilder build(){
            return new QueryBuilder(getQuerySelect(), getQueryFrom());
        }
    }

    public class Condition{
        private String column;
        private String whereClausal;
        private List<String> inListClausal;
        private QueryBuilder inQueryBuilderClausal;

        Condition(String column, String whereClausal){
            this.column = column;
            this.whereClausal = whereClausal;
        }

        Condition(String column, QueryBuilder queryBuilderList){
            this.column = column;
            this.inQueryBuilderClausal = queryBuilderList;
        }

        Condition(String column, List<String> listClausal){
           this.column = column;
           this.inListClausal = listClausal;
        }

        public Condition where(String column, String whereClausal){
            getQueryCondition().add(new Condition(column, whereClausal));
            return this;
        }

        public Condition in(String column, QueryBuilder inQueryBuilderClausal){
            getQueryCondition().add(new Condition(column, inQueryBuilderClausal));
            return this;
        }

        public Condition in(String column, List<String> inListClausal){
            getQueryCondition().add(new Condition(column, inListClausal));
            return this;
        }

        public QueryBuilder build(){
            return new QueryBuilder(getQuerySelect(), getQueryFrom(), getQueryCondition());
        }

        public String getColumn() {
            return column;
        }

        public String getWhereClausal() {
            return whereClausal;
        }

        public List<String> getInListClausal() {
            return inListClausal;
        }

        public QueryBuilder getInQueryBuilderClausal() {
            return inQueryBuilderClausal;
        }
    }

    public class Where{
        private String column;
        private String whereClausal;

        Where(String column, String whereClausal){
            this.column = column;
            this.whereClausal = whereClausal;
        }


        public Where where(String column, String whereClausal){
            Where where = new Where(column, whereClausal);
            queryWhere.add(where);
            return this;
        }

        public In in(String column, QueryBuilder inClausal){
            In in = new In(column, inClausal);
            queryIn.add(in);
            return in;
        }

        public In in(String column, List<String> inClausal){
            In in = new In(column, inClausal);
            queryIn.add(in);
            return in;
        }

        public QueryBuilder build(){
            return new QueryBuilder(getQuerySelect(), getQueryFrom(), queryWhere, queryIn);
        }
    }

    public class In{
        private Map<String, QueryBuilder> queryBuilderList;
        private Map<String, List<String>> in;

        In(String column, QueryBuilder queryBuilderList){
            this.queryBuilderList.put(column, queryBuilderList);
        }

        In(String column, List<String> in){
            this.in.put(column, in);
        }

        public Where where(String column, String whereClausal){
            Where where = new Where(column, whereClausal);
            queryWhere.add(where);
            return where;
        }

        public In in(String column, QueryBuilder inClausal){
            In in = new In(column, inClausal);
            queryIn.add(in);
            return in;
        }

        public In in(String column, List<String> inClausal){
            In in = new In(column, inClausal);
            queryIn.add(in);
            return in;
        }


        public QueryBuilder build(){
            return new QueryBuilder(getQuerySelect(), getQueryFrom(), queryWhere, queryIn);
        }

    }
}
