package api.bart.gov.wallet.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.bart.gov.wallet.entity.Ticket;
import api.bart.gov.wallet.entity.Transaction;
import api.bart.gov.wallet.entity.Wallet;
import api.bart.gov.wallet.repository.TicketRepository;
import api.bart.gov.wallet.repository.TransactionRepository;
import api.bart.gov.wallet.repository.WalletRepository;
import api.bart.gov.wallet.type.TransactionType;

@Service
public class WalletServiceImpl implements WalletService{
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public Wallet createWallet() {
		
		Wallet wallet = new Wallet();
		wallet.setWallet_id(UUID.randomUUID().toString());
		
		walletRepository.save(wallet);
		
		return wallet;
	}

	@Override
	public Wallet addPriceToWallet(String wallet_id, Float price) {
	
		Wallet wallet = walletRepository.findById(wallet_id).get();
		wallet.setAmount(wallet.getAmount() + price);
		
		Transaction transaction = new Transaction();
		transaction.setAmount(price);
		transaction.setTransaction_date(LocalDate.now());
		transaction.setWallet_id(wallet_id);
		transaction.setTransaction_id(UUID.randomUUID().toString());
		transaction.setType(TransactionType.CREDIT);
		
		walletRepository.save(wallet);
		transactionRepository.save(transaction);
		
		return wallet;
	}

	@Override
	public Ticket buyTicket(String wallet_id, Float price, String origin, String dest) throws Exception {
		
		Wallet wallet = walletRepository.findById(wallet_id).get();
		
		if (wallet.getAmount() < price) 
			throw new Exception("Insuffient Balance");
		
		wallet.setAmount(wallet.getAmount() - price);
		
		Transaction transaction = new Transaction();
		transaction.setAmount(price);
		transaction.setTransaction_date(LocalDate.now());
		transaction.setWallet_id(wallet_id);
		transaction.setTransaction_id(UUID.randomUUID().toString());
		transaction.setType(TransactionType.DEBIT);
		
		Ticket ticket = new Ticket();
		ticket.setDest(dest);
		ticket.setOrigin(origin);
		ticket.setTicketId(UUID.randomUUID().toString());
		ticket.setTrasaction_id(transaction.getTransaction_id());
		ticket.setWallet_id(wallet_id);
		ticket.setPrice(price);
		
		walletRepository.save(wallet);
		transactionRepository.save(transaction);
		ticketRepository.save(ticket);
		
		return ticket;
	}
}
