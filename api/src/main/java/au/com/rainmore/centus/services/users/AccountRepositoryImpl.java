package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.domains.users.QAccount;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class AccountRepositoryImpl
        extends QuerydslRepositorySupport
        implements AccountRepositoryCustom {

    public AccountRepositoryImpl() {
        super(Account.class);
    }

    @Override
    public Optional<Account> findOneByEmail(String email) {
        BooleanExpression criteria = QAccount.account.email.eq(email);
        Account account = this.from(QAccount.account)
                .where(criteria)
                .fetchOne();
        return Optional.ofNullable(account);
    }

}
