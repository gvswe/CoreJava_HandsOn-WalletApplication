package service;

import dto.Wallet;
import exception.WalletException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletServiceTest {
    WalletService walletService = new WalletServiceImpl();

    @BeforeAll
    public static void setupTestData() {
        System.out.println("Creating all test data");
    }

    @Test
    @Order(1)
    public void registerWalletTest() {

        try {
            Wallet wallet =walletService.registerWallet(new Wallet(5,"test1", 100.0, "test1p"));
            assertNotNull(wallet);
            assertEquals(5, wallet.getId());

        } catch (WalletException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    public void registerWalletExceptionTest() {
        assertThrows(WalletException.class,()-> walletService.registerWallet(new Wallet(5,"test1", 100.0, "test1p")));

    }

    @Test
    @Order(3)
    public void loginTest() {
        try {
            assertTrue(walletService.login(5, "test1p"));
        }catch (WalletException e){
            e.printStackTrace();
        }
    }

    @Order(4)
    @Test
    public void loginNullExceptionTest() {
        try {
            assertThrows(NullPointerException.class,()-> walletService.login(100,"test1p"));
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(5)
    @Test
    public void loginWalletExceptionTest(){
        assertThrows(WalletException.class,()-> walletService.login(5,"123"));
    }

    @Order(6)
    @Test
    public void showWalletBalanceTest() {

        try {
            assertEquals(100.0, walletService.showWalletBalance(5));
        } catch (WalletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(7)
    @Test
    public void showWalletBalanceExceptionTest() {
        try {
            assertThrows(NullPointerException.class,()-> walletService.showWalletBalance(100));
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(8)
    @Test
    public void addFundsTest() {

        try {
            assertEquals(300.0, walletService.addFundsToWallet(5,200.0));
        } catch (WalletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(9)
    @Test
    public void addFundsNullExceptionTest(){
        try {
            assertThrows(NullPointerException.class,()-> walletService.addFundsToWallet(100,100.0));
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(10)
    @Test
    public void addFundsWalletExceptionTest(){
        assertThrows(WalletException.class,()-> walletService.addFundsToWallet(5,0.0));
    }

    @Order(11)
    @Test
    public void fundTransferTest() {

        try {
            Wallet wallet =walletService.registerWallet(new Wallet(6,"test2", 200.0, "test2p"));
            assertNotNull(wallet);
            assertTrue(walletService.fundTransfer(6,5,100.0));
        } catch (WalletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(12)
    @Test
    public void fundTransferNullExceptionTest(){
        try {
            assertThrows(NullPointerException.class,()-> walletService.fundTransfer(100,1,100.0));
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(13)
    @Test
    public void fundTransferWalletExceptionTest(){
            assertThrows(WalletException.class,()-> walletService.fundTransfer(6,5,200.0));
    }

    @Order(14)
    @Test
    public void withdrawTest() {

        try {
            assertEquals(300.0, walletService.withdrawFunds(5,100.0));
        } catch (WalletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(15)
    @Test
    public void withdrawNullExceptionTest(){
        try {
            assertThrows(NullPointerException.class,()-> walletService.withdrawFunds(100,100.0));
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(16)
    @Test
    public void withdrawWalletExceptionTest(){
        assertThrows(WalletException.class,()-> walletService.withdrawFunds(6,200.0));
    }

    @Order(17)
    @Test
    public void unregisterTest() {

        try {
            Wallet wallet1 =walletService.unRegisterWallet(5,"test1p");
            assertNotNull(wallet1);
            assertEquals(5, wallet1.getId());
            Wallet wallet2=walletService.unRegisterWallet(6,"test2p");
            assertNotNull(wallet2);
            assertEquals(6, wallet2.getId());
        } catch (WalletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(18)
    @Test
    public void unregisterNullExceptionTest() {
        try {
            assertThrows(NullPointerException.class,()-> walletService.unRegisterWallet(100,"123"));
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Order(19)
    @Test
    public void unregisterWalletExceptionTest() {
        assertThrows(WalletException.class,()-> walletService.unRegisterWallet(4,"123"));
    }


    @AfterAll
    public static void destroyTestData() {
        System.out.println("Cleared all test data");
    }
}