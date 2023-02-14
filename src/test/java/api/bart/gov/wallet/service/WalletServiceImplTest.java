package api.bart.gov.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import api.bart.gov.wallet.entity.Ticket;
import api.bart.gov.wallet.entity.Wallet;
import api.bart.gov.wallet.repository.TicketRepository;
import api.bart.gov.wallet.repository.TransactionRepository;
import api.bart.gov.wallet.repository.WalletRepository;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

	@InjectMocks
	private WalletServiceImpl walletServiceImpl;

	@Mock
	private WalletRepository walletRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private TicketRepository ticketRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testCreateWallet() {
		
		Wallet wallet = walletServiceImpl.createWallet();
		
		assertNotNull(wallet);
	}
	
	@Test 
	public void testAddPriceToWallet() {
		
		Wallet wallet = new Wallet();
		wallet.setAmount(200);
		wallet.setWallet_id("123456789");
		
		when(walletRepository.findById(anyString())).thenReturn(Optional.of(wallet));
		Wallet result = walletServiceImpl.addPriceToWallet("123456789", (float) 300.0);
		
		assertEquals(result.getAmount(), 500);
		assertEquals(result.getWallet_id(),wallet.getWallet_id());
	}
	
	@Test 
	public void testbuyTicket() throws Exception {
		
		Wallet wallet = new Wallet();
		wallet.setAmount(200);
		wallet.setWallet_id("123456789");
		
		when(walletRepository.findById(anyString())).thenReturn(Optional.of(wallet));
		Ticket result = walletServiceImpl.buyTicket("123456789", 100f, "12TH", "EMBR");
		
		assertEquals(result.getWallet_id(), wallet.getWallet_id());
		assertEquals(result.getPrice(), 100f);
		assertEquals(wallet.getAmount(), 100);
	}
	
	@Test
	public void testbuyTicketException() throws Exception {
		
		Wallet wallet = new Wallet();
		wallet.setAmount(200);
		wallet.setWallet_id("123456789");
		
		when(walletRepository.findById(anyString())).thenReturn(Optional.of(wallet));
		assertThrows(Exception.class, () -> {
			walletServiceImpl.buyTicket("123456789", 500f, "12TH", "EMBR");
			});
		

	}
}
