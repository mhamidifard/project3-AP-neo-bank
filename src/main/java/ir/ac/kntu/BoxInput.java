package ir.ac.kntu;

import java.util.List;

public class BoxInput {
    private static Account account;

    public static void goBoxes(Account account1) {
        account = account1;
        int choice = 0;
        while (true) {
            Print.menu("1.create new box\n2.list of boxes");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    switchCreateBox();
                    break;
                case 2:
                    listBoxes();
                    break;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void switchCreateBox() {
        int choice = 0;
        while (true) {
            Print.info("create Box");
            Print.menu("1.Simple box\n2.reward box\n3.small money box");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    createSimpleBox();
                    break;
                case 2:
                    createRewardBox();
                    break;
                case 3:
                    createSmallMoneyBox();
                    break;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void createSimpleBox() {
        account.addBox(new Box(0L, TypeBox.SIMPLE));
    }

    public static void createRewardBox() {
        long days;
        while (true) {
            Print.input("enter days: (30<=days<=356)");
            days = UserInput.simpleLong();
            if (days == -1) {
                return;
            }
            if (days >= 30 && days <= 356) {
                break;
            } else {
                Print.erorr("invalid days");
            }
        }
        long money = inDeposit();
        if (money == -1) {
            return;
        }
        Box box = new RewardBox(0L, days);
        account.addBox(box);
        account.deposit(box, money);
    }

    public static long inDeposit() {
        long money;
        while (true) {
            Print.input("enter amount money to deposit to box:");
            money = UserInput.simpleLong();
            if (money == -1) {
                return -1;
            }
            if (money > account.getBalance()) {
                Print.erorr("balance does not enough");
            } else {
                return money;
            }
        }
    }

    public static long inWithdraw(Box box) {
        long money;
        while (true) {
            Print.input("enter amount money to withdraw to box:");
            money = UserInput.simpleLong();
            if (money == -1) {
                return -1;
            }
            if (money > box.getBalance()) {
                Print.erorr("box balance does not enough");
            } else {
                return money;
            }
        }
    }

    public static void createSmallMoneyBox() {
        if (account.getSmallMoneyBox() != null) {
            Print.erorr("You already have this box");
            return;
        }
        account.addBox(new Box(0L, TypeBox.SMALLMONEY));
    }

    public static void listBoxes() {
        int choice;
        List<Box> boxes = account.getBoxes();
        while (true) {
            for (int i = 0; i < boxes.size(); i++) {
                Print.list(i + 1 + "." + boxes.get(i).summery());
            }
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            if (0 < choice && choice <= boxes.size()) {
                switchShowBox(boxes.get(choice - 1));
            } else {
                Print.erorr("invalid choice");
            }
        }
    }

    public static void switchShowBox(Box box) {
        if (box.getType() == TypeBox.REWARD) {
            RewardBox rewardBox = (RewardBox) box;
            if (!rewardBox.isProfeted()) {
                int choice;
                Print.info(rewardBox.toString());
                while (true) {
                    choice = UserInput.simpleMenu();
                    if (choice == -1) {
                        return;
                    } else {
                        Print.erorr("invalid choice");
                    }
                }
            } else {
                showRewardBox(box);
            }

        } else if (box.getType() == TypeBox.SMALLMONEY) {
            showSmallBox(box);
        } else if (box.getType() == TypeBox.SIMPLE) {
            showSimpleBox(box);
        }
    }

    public static void showRewardBox(Box box) {
        RewardBox rewardBox = (RewardBox) box;
        int choice = 0;
        while (true) {
            Print.info(rewardBox.toString());
            Print.menu("1.withdraw\n2.delete box");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    withdrawBox(box);
                    break;
                case 2:
                    deleteBox(box);
                    return;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }


    public static void withdrawBox(Box box) {
        if (box.getType() == TypeBox.REWARD && !((RewardBox) box).isProfeted()) {
            Print.erorr("please wait to pay profit");
            return;
        }
        long money = inWithdraw(box);
        if (money == -1) {
            return;
        }
        account.withdraw(box, money);
    }

    public static void deleteBox(Box box) {
        if (box.getBalance() > 0) {
            Print.erorr("box already have money");
            return;
        }
        account.removeBox(box);
    }

    public static void showSmallBox(Box box) {
        int choice = 0;
        while (true) {
            Print.info(box.toString());
            Print.menu("1.withdraw\n2.delete box");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    withdrawBox(box);
                    break;
                case 2:
                    deleteBox(box);
                    return;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void showSimpleBox(Box box) {
        int choice = 0;
        while (true) {
            Print.info(box.toString());
            Print.menu("1.withdraw\n2.deposit\n3.delete box");
            choice = UserInput.simpleMenu();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    withdrawBox(box);
                    break;
                case 2:
                    depositBox(box);
                    break;
                case 3:
                    deleteBox(box);
                    return;

                default:
                    Print.erorr("invalid choice");
                    break;
            }
        }
    }

    public static void depositBox(Box box) {
        long money = inDeposit();
        if (money == -1) {
            return;
        }
        account.deposit(box, money);

    }


}
