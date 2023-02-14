package api.bart.gov.wallet.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;

import api.bart.gov.wallet.type.TransactionType;

@Entity
public class Transaction {

	@Id
	private String transaction_id;
	private LocalDate transaction_date;
	private Float amount;
	private TransactionType type;
	private String wallet_id;
	
	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public LocalDate getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}

	public String getWallet_id() {
		return wallet_id;
	}

	public void setWallet_id(String wallet_id) {
		this.wallet_id = wallet_id;
	}
}