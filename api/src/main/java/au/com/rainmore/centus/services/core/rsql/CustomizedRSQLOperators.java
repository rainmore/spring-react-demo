package au.com.rainmore.centus.services.core.rsql;

import cz.jirutka.rsql.parser.ast.Arity;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public enum CustomizedRSQLOperators {
    EQUAL(RSQLOperators.EQUAL),
    NOT_EQUAL(RSQLOperators.NOT_EQUAL),
    GREATER_THAN(RSQLOperators.GREATER_THAN),
    GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),
    MIN(RSQLOperators.GREATER_THAN_OR_EQUAL),
    LESS_THAN(RSQLOperators.LESS_THAN),
    LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),
    MAX(RSQLOperators.LESS_THAN_OR_EQUAL),
    IN(RSQLOperators.IN),
    NOT_IN(RSQLOperators.NOT_IN),
    NOT_NULL(RSQLOperators.NOT_NULL),
    EXISTS(RSQLOperators.NOT_NULL),
    IS_NULL(RSQLOperators.IS_NULL),
    NOT_EXISTS(RSQLOperators.IS_NULL),
    // simplified for providing both MAX and MIN
    BETWEEN(new ComparisonOperator("=between=", Arity.nary(2))),
    TRUTHY(new ComparisonOperator("=istrue=", Arity.nary(0))),
    FALSY(new ComparisonOperator("=isfalse=", Arity.nary(0)));

    private final ComparisonOperator operator;

    CustomizedRSQLOperators(ComparisonOperator operator) {
        this.operator = operator;
    }
}
