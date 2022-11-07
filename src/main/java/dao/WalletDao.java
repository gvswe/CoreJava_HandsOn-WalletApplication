package dao;
import dto.Wallet;
import exception.WalletException;

import java.sql.SQLException;
import java.util.Optional;

public interface WalletDao {
	//CRUD
	Wallet addWallet(Wallet newWallet)throws WalletException;
	Wallet getWalletById(Integer walletId)throws WalletException;

	Wallet updateWallet(Wallet updateWallet)throws WalletException;
	Wallet deleteWalletById(Integer walletID)throws WalletException;
}
