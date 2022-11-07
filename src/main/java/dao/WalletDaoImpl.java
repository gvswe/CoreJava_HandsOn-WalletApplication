package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dto.Wallet;
import exception.WalletException;

public class WalletDaoImpl implements WalletDao {

	private Connection connection;

	public WalletDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	public Wallet addWallet(Wallet newWallet) throws WalletException {

		String sql = "INSERT INTO wallet_info (ID,FullName,Balance,AccPassword) VALUES(?,?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, newWallet.getId());
			preparedStatement.setString(2, newWallet.getName());
			preparedStatement.setDouble(3, newWallet.getBalance());
			preparedStatement.setString(4, newWallet.getPassword());
			//System.out.println(preparedStatement);
			Integer count = preparedStatement.executeUpdate();
			if (count != 1)
				//System.out.println("Wallet Account could not be added.");
				throw new WalletException("Wallet Account could not be added.");

		} catch (SQLException e) {//e.printStackTrace();
			throw new WalletException("Connection failed.");
		}

		return newWallet;
	}


	public Wallet getWalletById(Integer walletId) throws WalletException {

		String sql = "SELECT * FROM wallet_info WHERE ID=?";
		//Optional<Wallet> optWallet;
		Wallet newWallet = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, walletId);
			//System.out.println(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				// if walletAcc exists for given id
				newWallet = new Wallet(resultSet.getInt("ID"), resultSet.getString("FullName"), resultSet.getDouble("Balance"),
						resultSet.getString("AccPassword"));
				//optWallet= Optional.of(newWallet);
				//return optWallet;
				return newWallet;
			}
		} catch (SQLException e) {
			throw new WalletException("Connection failed.");
		}//optWallet= Optional.empty();
		//return optWallet;
		return newWallet;
	}

	public Wallet updateWallet(Wallet updateWallet) throws WalletException {

		Wallet newWallet = null;
		try {
			String sql = "UPDATE wallet_info set Balance=? WHERE ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, updateWallet.getBalance());
			preparedStatement.setInt(2, updateWallet.getId());
			//System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			String sql1 = "SELECT * FROM wallet_info WHERE ID=?";
			try {
				PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
				preparedStatement1.setInt(1, updateWallet.getId());
				//System.out.println(preparedStatement1);
				ResultSet resultSet = preparedStatement1.executeQuery();
				if (resultSet.next()) {
					newWallet = new Wallet(resultSet.getInt("ID"), resultSet.getString("FullName"), resultSet.getDouble("Balance"),
							resultSet.getString("AccPassword"));
					System.out.println("Wallet account updated successfully.");
					return newWallet;
				}
			} catch (SQLException e) {
				throw new WalletException("Connection failed.");
			}

		} catch (SQLException e) {
			throw new WalletException("Connection failed.");
		}
		return newWallet;
	}

		public Wallet deleteWalletById (Integer walletID) throws WalletException {

			Wallet newWallet = null;
			String sql1 = "SELECT * FROM wallet_info WHERE ID=?";
			try {
				PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
				preparedStatement1.setInt(1, walletID);
				//System.out.println(preparedStatement1);
				ResultSet resultSet = preparedStatement1.executeQuery();
				if (resultSet.next()) {
					newWallet = new Wallet(resultSet.getInt("ID"), resultSet.getString("FullName"), resultSet.getDouble("Balance"),
							resultSet.getString("AccPassword"));
					try {
						String sql = "DELETE FROM wallet_info WHERE id = ?";
						PreparedStatement preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setInt(1, walletID);
						// System.out.println(preparedStatement);
						preparedStatement.execute();
						System.out.println("Wallet Account Deleted Successfully.");

					} catch (SQLException e) {
						throw new WalletException("Connection failed.");
					}
					return newWallet;

				}
			} catch (SQLException e) {
				throw new WalletException("Connection failed.");
			}
			return newWallet;

		}
	}