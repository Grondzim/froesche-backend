package de.froesche.nz.querybuilder;

import java.util.Map;

public class DeleteQueryBuilder extends QueryBuilder<DeleteQueryBuilder>{
    @Override
    protected DeleteQueryBuilder buildQuery() {
        return new DeleteQueryBuilder(fromBuilder, isConditionBuilder);
    }

    public static DeleteQueryBuilder query(){
        return new DeleteQueryBuilder();
    }

    protected DeleteQueryBuilder(){}

    protected DeleteQueryBuilder(From fromBuilder, IsCondition conditions){
        this.fromBuilder = fromBuilder;
        this.isConditionBuilder = conditions;
    }

    public From getFromBuilder() {
        return fromBuilder;
    }

    public IsCondition getConditions() {
        return isConditionBuilder;
    }

    public From delete(Map<String,String> fromMap){
        fromBuilder = new From(fromMap);
        return fromBuilder;
    }
}
