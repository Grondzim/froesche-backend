package de.froesche.nz.querybuilder;

public class SelectQueryBuilder extends AbstractQueryBuilder<SelectQueryBuilder> {

    @Override
    protected SelectQueryBuilder buildQuery() {
        return new SelectQueryBuilder(selectBuilder,fromBuilder,isConditionBuilder);
    }

    public static SelectQueryBuilder query(){
        return new SelectQueryBuilder();
    }

    protected SelectQueryBuilder(){}

    protected SelectQueryBuilder(Select selectBuilder, From fromBuilder, IsCondition conditions){
        this.selectBuilder = selectBuilder;
        this.fromBuilder = fromBuilder;
        this.isConditionBuilder = conditions;
    }

    public Select getSelectBuilder() {
        return selectBuilder;
    }

    public From getFromBuilder() {
        return fromBuilder;
    }

    public IsCondition getConditions() {
        return isConditionBuilder;
    }

    public Select select(String... columns){
        selectBuilder = new Select(columns);
        return getSelectBuilder();
    }
}
