package de.froesche.nz.querybuilder;

public class InsertQueryBuilder extends AbstractQueryBuilder<InsertQueryBuilder> {


    public static SelectQueryBuilder query(){
        return new SelectQueryBuilder();
    }

    protected InsertQueryBuilder(){}

    protected InsertQueryBuilder(Insert insertBuilder, Into intoBuilder){
        this.insertBuilder = insertBuilder;
        this.intoBuilder = intoBuilder;
    }

    @Override
    protected InsertQueryBuilder buildQuery() {
        return new InsertQueryBuilder(getInsertBuilder(), getIntoBuilder());
    }

    public Insert getInsertBuilder() {
        return insertBuilder;
    }

    public Into getIntoBuilder() {
        return intoBuilder;
    }

    public Insert insert(String... columns){
        insertBuilder = new Insert(columns);
        return insertBuilder;
    }
}
