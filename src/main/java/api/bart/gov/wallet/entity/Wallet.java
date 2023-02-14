package api.bart.gov.wallet.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Wallet {

	@Id	
	private String wallet_id;
	
	private float amount;
	
	public String getWallet_id() {
		return wallet_id;
	}
	public void setWallet_id(String wallet_id) {
		this.wallet_id = wallet_id;
	}
	
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}	
}
