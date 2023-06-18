//import java.util.Arrays;
//import java.util.concurrent.locks.ReentrantLock;
//
//
//
//
//public class Main {
//    static int n = 100;
//    static int initial = 1000;
//    static int maxAmount = 1000;
//    static int delay = 1000;
//
//    public static void main(String[] args) {
//        var bank = new Bank(n, initial);
//        for (int i = 0; i < n; i++) {
//            int fromAccount = i;
//            Runnable r = () -> {
//                try {
//                    while (true) {
//                        int toAccount = (int) (n * Math.random());
//                        double amount = 990 + maxAmount * Math.random();
//                        bank.transfer(fromAccount, toAccount, amount);
//                        Thread.sleep((int) (delay * Math.random()));
//                    }
//                } catch (InterruptedException ignored) {
//                }
//            };
//            var t = new Thread(r);
//            t.start();
//        }
//
//    }
//}
//
//class Bank {
//    public final double[] accounts;
//
//    final ReentrantLock lock = new ReentrantLock();
//
//    public Bank(int n, int initial) {
//        this.accounts = new double[n];
//        Arrays.fill(this.accounts, initial);
//    }
//
//    public double getTotalBalance() {
//        return Arrays.stream(accounts).sum();
//    }
//
public synchronized void transfer(int from,int to,double amount)throws InterruptedException{
//
        try{
        while(accounts[from]<amount){
        wait();
        }
        System.out.println(Thread.currentThread());
        accounts[from]-=amount;
        System.out.printf(" %10.2f from %d to %d%n",amount,from,to);
        accounts[to]+=amount;
        System.out.printf(" Total balance: %10.2f%n",getTotalBalance());
        notifyAll();
        }
        }
        }