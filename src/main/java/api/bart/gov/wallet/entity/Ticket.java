package api.bart.gov.wallet.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ticket {
	
	@Id
	private String ticketId;
	
	private String wallet_id;
	
	private String trasaction_id;
	
	private String origin;
	
	private String dest;

	private float price;
	
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getWallet_id() {
		return wallet_id;
	}

	public void setWallet_id(String wallet_id) {
		this.wallet_id = wallet_id;
	}

	public String getTrasaction_id() {
		return trasaction_id;
	}

	public void setTrasaction_id(String trasaction_id) {
		this.trasaction_id = trasaction_id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
