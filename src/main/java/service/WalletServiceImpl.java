package service;
import dao.DaoUtility;
import dao.WalletDao;
import dao.WalletDaoImpl;
import dto.Wallet;
import exception.WalletException;



public class WalletServiceImpl implements WalletService {

	private WalletDao walletRepository = new WalletDaoImpl(DaoUtility.getConnectionToMySQL());


	public Wallet registerWallet(Wallet newWallet) throws WalletException{
		Integer existId=newWallet.getId();
		Wallet loginId=this.walletRepository.getWalletById(existId);
		if (loginId == null) {
			return this.walletRepository.addWallet(newWallet);
		}
		else throw new WalletException("ID "+existId+" already present.Enter new ID.");
	}

	public Boolean login(Integer walletId, String password) throws WalletException,NullPointerException {
		Wallet loginId = this.walletRepository.getWalletById(walletId);
		if(loginId==null) throw new NullPointerException("Login ID "+walletId+ " doesnt exist.");
		String pswrd=loginId.getPassword();
		if(password.equals(pswrd)){
			return true;
		}
		throw new WalletException("Login failed.Enter correct password.");

	}

	public Double addFundsToWallet(Integer walletId, Double amount) throws WalletException {
		Wallet loginId = this.walletRepository.getWalletById(walletId);
		if(loginId==null) throw new NullPointerException("Login ID "+walletId+ " doesnt exist.");
		Double balanceamount=loginId.getBalance();
		if(amount>0.0) {
			balanceamount+=amount;
			loginId.setBalance(balanceamount);
			Wallet updatedWallet=this.walletRepository.updateWallet(loginId);
			return balanceamount;
		}
		else throw new WalletException("Enter atleast Rs.1 amount to add.");
	}


	public Double showWalletBalance(Integer walletId) throws WalletException {
		Wallet loginId = this.walletRepository.getWalletById(walletId);
		if(loginId==null) throw new NullPointerException("Login ID "+walletId+ " doesnt exist.");
		Double balanceamount=loginId.getBalance();
		return balanceamount;
	}


	public Boolean fundTransfer(Integer fromId, Integer toId, Double amount) throws WalletException {
		Wallet fromAccId = this.walletRepository.getWalletById(fromId);
		Wallet toAccId = this.walletRepository.getWalletById(toId);
		if(fromAccId==null) throw new NullPointerException("From ID doesn't exist.");
		if(toAccId==null) throw new NullPointerException("From ID doesn't exist.");
		Double fromBalance = fromAccId.getBalance();
		Double toBalance = toAccId.getBalance();
		if(fromBalance>=amount) {
			fromBalance-=amount;
			toBalance+=amount;
			//if(fromBalance>=500) {
				fromAccId.setBalance(fromBalance);
				toAccId.setBalance(toBalance);
				Wallet updatedFrom=this.walletRepository.updateWallet(fromAccId);
				Wallet updatedTo=this.walletRepository.updateWallet(toAccId);
				return true;
		}
		else
			throw new WalletException("No minimum balance to transfer amount.");
	}

	public Wallet unRegisterWallet(Integer walletId, String password) throws WalletException {
		Wallet loginId = this.walletRepository.getWalletById(walletId);
		if(loginId==null) throw new NullPointerException("Login ID "+walletId+ " doesnt exist.");
		String pswrd=loginId.getPassword();
		if(password.equals(pswrd)){
			Wallet deletedWallet=this.walletRepository.deleteWalletById(walletId);
			return deletedWallet;
		}
		else
			throw new WalletException("Password incorrect.Cannot unregister.");
	}
	public Double withdrawFunds(Integer walletId, Double amount) throws WalletException{
		Wallet loginId = this.walletRepository.getWalletById(walletId);
		if(loginId==null) throw new NullPointerException("Login ID "+walletId+ " doesnt exist.");
		Double balanceamount=loginId.getBalance();
		if(amount<=balanceamount) {
			balanceamount-=amount;
			loginId.setBalance(balanceamount);
			Wallet updatedWallet=this.walletRepository.updateWallet(loginId);
			return balanceamount;
		}
		else
			throw new WalletException("No minimum balance to withdraw Rs." +amount);
	}
}

