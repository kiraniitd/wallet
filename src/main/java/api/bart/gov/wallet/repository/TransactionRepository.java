package api.bart.gov.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.bart.gov.wallet.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
