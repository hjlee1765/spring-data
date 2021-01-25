package me.whiteship.querydsldemo.account;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void crud(){
        Account account = new Account();
        account.setFirstName("hyungjun");
        account.setLastName("lee");
        accountRepository.save(account);

        assertEquals(1, accountRepository.findAll().size());

        Predicate predicate = QAccount.account.firstName.containsIgnoreCase("hyungjun")
                .and(QAccount.account.lastName.startsWith("lee"));

        Optional<Account> one = accountRepository.findOne(predicate);
        assertThat(one).isNotEmpty();
    }
}