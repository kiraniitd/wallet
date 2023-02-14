package api.bart.gov.wallet.service;

import org.springframework.stereotype.Service;

import api.bart.gov.wallet.entity.Ticket;
import api.bart.gov.wallet.entity.Wallet;

public interface WalletService {	

	public Wallet createWallet();
	
	public Wallet addPriceToWallet(String wallet_id, Float price);
	
	public Ticket buyTicket(String wallet_id, Float price, String origin, String dest) throws Exception;
}
