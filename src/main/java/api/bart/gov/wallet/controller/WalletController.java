package api.bart.gov.wallet.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.bart.gov.wallet.entity.Wallet;
import api.bart.gov.wallet.service.WalletService;

@RestController
public class WalletController {

	private static Logger logger = LoggerFactory.getLogger(WalletController.class);
	
	@Autowired
	private WalletService walletService;
	
	@Value("${apikey}")
	private String apikey;

	@Value("${fareUrl}")
	private String fareUrl;
	
	/**
	 * Gets fare for given origin and destination
	 * @param origin
	 * @param dest
	 * @return
	 */
	@GetMapping("/route/{origin}/{dest}")
	public ResponseEntity<String> getfare(@PathVariable(name = "origin") String origin, @PathVariable(name = "dest") String dest) {

		try {
			RestTemplate rt = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "*/*");
			headers.add("authority", "api.bart.gov");
			headers.add("Connection", "keep-alive");
			headers.add("content-type", "application/x-www-form-urlencoded");
			headers.add("Cache-Control", "no-cache");
			headers.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			String url = fareUrl.replace("{origin}", origin).replace("{dest}", dest).replace("{key}", apikey);
			
			ResponseEntity<String> res = rt.exchange(url, HttpMethod.GET, entity, String.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			TypeReference<Map<String, Object>> type = new TypeReference<Map<String, Object>>() {
			};
			 
			if (res.getBody() != null) {
				
				Map<String, Object> responseBody = mapper.readValue(res.getBody(), type);

				HashMap<String, Object> root = (HashMap<String, Object>) responseBody.get("root");

				HashMap<String, Object> trip = (HashMap<String, Object>) root.get("trip");

				return new ResponseEntity<>((String) trip.get("fare"), HttpStatus.OK);
			}
			
		} catch (Exception ex) {
			logger.error("Error Occured While retreiving data {}", ex);
			return new ResponseEntity<>("Error Occured While retreiving data", HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}
	
	
	/**
	 * Gets the ticket for the given origin, destination and wallet_id
	 * @param wallet_id
	 * @param origin
	 * @param dest
	 * @return
	 */
	@GetMapping("/ticket/{wallet_id}/{origin}/{dest}")
	public ResponseEntity<String> buyTicket(@PathVariable(name = "wallet_id") String wallet_id, 
			@PathVariable(name = "origin") String origin, @PathVariable(name = "dest") String dest) {
		
		try {
			
			ResponseEntity<String> entity = getfare(origin, dest);
			Float fare = Float.valueOf(entity.getBody());
			walletService.buyTicket(wallet_id, fare, origin, dest);
			
		}
		catch (Exception ex) {
			logger.error("Error Occured while buying ticket {}", ex);
			return new ResponseEntity<>("Error Occured while buying ticket", HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}
	
	/**
	 * Creates Wallet
	 * @return
	 */
	@GetMapping("/wallet/create") 
	public ResponseEntity<Wallet> createWallet() {
		try {
		     Wallet wallet = walletService.createWallet();
		     
		     return new ResponseEntity<>(wallet, HttpStatus.OK);
		}
		catch (Exception ex) {
			logger.error("Error Occured while Creating wallet {}", ex);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	/** 
	 * Adds money to given wallet
	 * @param wallet_id
	 * @param price
	 * @return
	 */
	@GetMapping("/wallet/addmoney/{wallet_id}/{price}")
	public ResponseEntity<Wallet> addMoney(@PathVariable(name = "wallet_id") String wallet_id, @PathVariable(name = "price") Float price) {
		try {
		     Wallet wallet = walletService.addPriceToWallet(wallet_id, price);
		     
		     return new ResponseEntity<>(wallet, HttpStatus.OK);
		}
		catch (Exception ex) {
			logger.error("Error Occured while Creating wallet {}", ex);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
