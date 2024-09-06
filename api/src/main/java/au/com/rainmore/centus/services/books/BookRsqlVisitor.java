package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.models.books.Book;
import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class BookRsqlVisitor implements RSQLVisitor<Book, Void> {

    @Override
    public Book visit(AndNode andNode, Void unused) {
        return null;
    }

    @Override
    public Book visit(OrNode orNode, Void unused) {
        return null;
    }

    @Override
    public Book visit(ComparisonNode comparisonNode, Void unused) {
        return null;
    }
}
