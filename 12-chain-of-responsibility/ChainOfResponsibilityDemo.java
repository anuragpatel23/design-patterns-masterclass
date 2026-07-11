package chainofresponsibility;

/**
 * Chain of Responsibility — expense approval escalating by amount.
 * Run: javac ChainOfResponsibilityDemo.java && java chainofresponsibility.ChainOfResponsibilityDemo
 */
public class ChainOfResponsibilityDemo {

    static abstract class Approver {
        protected Approver next;
        Approver linkTo(Approver next) { this.next = next; return next; }
        void handle(String item, long amount) {
            if (canApprove(amount)) approve(item, amount);
            else if (next != null) next.handle(item, amount);
            else System.out.println("No one can approve " + item + " ($" + amount + ")");
        }
        abstract boolean canApprove(long amount);
        abstract void approve(String item, long amount);
    }

    static class TeamLead extends Approver {
        boolean canApprove(long a) { return a <= 1_000; }
        void approve(String i, long a) { System.out.println("TeamLead approved " + i + " ($" + a + ")"); }
    }
    static class Manager extends Approver {
        boolean canApprove(long a) { return a <= 10_000; }
        void approve(String i, long a) { System.out.println("Manager approved " + i + " ($" + a + ")"); }
    }
    static class Director extends Approver {
        boolean canApprove(long a) { return a <= 100_000; }
        void approve(String i, long a) { System.out.println("Director approved " + i + " ($" + a + ")"); }
    }

    public static void main(String[] args) {
        Approver chain = new TeamLead();
        chain.linkTo(new Manager()).linkTo(new Director());   // build the chain

        chain.handle("Keyboards", 500);
        chain.handle("Team offsite", 8_000);
        chain.handle("New servers", 75_000);
        chain.handle("Acquisition", 5_000_000);  // falls off the end
    }
}
