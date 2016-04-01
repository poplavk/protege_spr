import de.vandermeer.skb.asciitable.AsciiTable;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import org.apache.commons.lang3.RandomStringUtils;
import protege_generated.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {

    public static void main(String []args) {
        final String fileName = "protege_project/spr.pprj";

        Collection errors = new ArrayList<>();
        Project project = new Project(fileName, errors);
        KnowledgeBase kb = project.getKnowledgeBase();

        MyFactory factory = new MyFactory(kb);
        printRequirements(factory);
        printKnowledgeBase(factory);
        Case c = enterData(factory);
        if (validationClient(factory, c) == true) {
            takeDecision(factory, c, true);
        }
        else {
            System.out.println("DecisionSPR: FAILURE");
            c.setDecisionSPR("failure");
        }
        printKnowledgeBase(factory);

        project.save(errors);
    }

    public static void printKnowledgeBase(MyFactory factory) {
        Collection<Case> cases = factory.getAllCaseObjects();

        AsciiTable at=AsciiTable.newTable(3, 180);
        at.addRow(null, null, "Poplabank");
        at.addRow("Client", "Credit Info", "Decision");

        for (Case c : cases) {
            at.addRow(getClientString(c.getClient()), getCreditString(c.getCredit()), c.getDecisionSPR());
        }
        System.out.println(at.render());
    }

    public static  void printRequirements(MyFactory factory){
        Collection<Requirements> requirementses = factory.getAllRequirementsObjects();
        AsciiTable at=AsciiTable.newTable(3, 50);
        at.addRow(null, null, "Requirements");
        at.addRow("SlotName", "Condition", "Value");
        for (Requirements r : requirementses) {
            at.addRow(r.getSlotName(), r.getCond(), r.getValue());
        }
        System.out.println(at.render());
    }

    public static String getClientString(Client c) {
        String string = new String();
        string = "Full Name: " + c.getFullName() + " Age: " + c.getAge() + " Phone: " + c.getPhone() +
                " Registration flag: " + c.getRegistration() + " BlackList flag: " + c.getInBlackList() + " " +
                getDocumentString(c.getDocument()) + " Organization: " + c.getJob().getOrganization() +
                " Position: " + c.getJob().getPosition() + " Salary: " + c.getJob().getSalary();
        return string;
    }

    public static String getDocumentString(Document d) {
        return new String("Document type: " + d.getType() + " Document seria: " + d.getSeria() +
                " Document number: " + d.getNumber() + " Document validation: " + d.getValid());
    }

    public static  String getCreditString(CreditData c) {
        return new String("Cient income: " + c.getIncome() + " Client expense: " + c.getExpense() + " Credit sum: " +
                c.getSum() + " Credit term: " + c.getTerm());
    }

    public static Case enterData(MyFactory factory) {
        Client newClient = factory.createClient("client_" + RandomStringUtils.randomAlphanumeric(16));
        Document newDocument = factory.createDocument("document_" + RandomStringUtils.randomAlphanumeric(16));
        Job newJob = factory.createJob("job_" + RandomStringUtils.randomAlphanumeric(16));
        CreditData newCreditData = factory.createCreditData("creditData_" + RandomStringUtils.randomAlphanumeric(16));
        Case newCase = factory.createCase("case_" + RandomStringUtils.randomAlphanumeric(16));

        Scanner input = new Scanner(System.in);

        System.out.println("-----------------");
        System.out.println("Enter client data");
        System.out.println("-----------------");
        System.out.println("Enter full name:");
        newClient.setFullName(input.nextLine());
        System.out.println("Enter age:");
        newClient.setAge(input.nextInt());
        System.out.println("Enter phone:");
        newClient.setPhone(input.next());
        System.out.println("Enter registration flag (true or false):");
        newClient.setRegistration(input.nextBoolean());
        System.out.println("Enter black list flag (true or false):");
        newClient.setInBlackList(input.nextBoolean());

        System.out.println("---------------------------");
        System.out.println("Enter clients document data");
        System.out.println("---------------------------");
        System.out.println("Enter type (passport or other):");
        newDocument.setType(input.next());
        System.out.println("Enter seria (XXXX):");
        newDocument.setSeria(input.nextInt());
        System.out.println("Enter number (XXXXXX):");
        newDocument.setNumber(input.nextInt());
        System.out.println("Document valid (true or false):");
        newDocument.setValid(input.nextBoolean());

        System.out.println("----------------------");
        System.out.println("Enter clients job data");
        System.out.println("----------------------");
        System.out.println("Enter organization:");
        newJob.setOrganization(input.next());
        System.out.println("Enter position:");
        newJob.setPosition(input.next());
        System.out.println("Enter salary:");
        newJob.setSalary(input.nextInt());

        System.out.println("------------------");
        System.out.println("Enter credit data");
        System.out.println("------------------");
        System.out.println("Enter income:");
        newCreditData.setIncome(input.nextInt());
        System.out.println("Enter expense:");
        newCreditData.setExpense(input.nextInt());
        System.out.println("Enter sum:");
        newCreditData.setSum(input.nextInt());
        System.out.println("Enter term:");
        newCreditData.setTerm(input.nextInt());

        newClient.setDocument(newDocument);
        newClient.setJob(newJob);
        newCase.setClient(newClient);
        newCase.setCredit(newCreditData);
        newCase.setDecisionSPR("undefined");
        //System.out.println(getClientString(newClient));
        return newCase;

    }

    public static void takeDecision(MyFactory factory, Case c, Boolean validKof) {
        Collection<Case> cases = factory.getAllCaseObjects();
        Case offer = null;
        double S;
        double min = 1000000000;
        for (Case k : cases) {
            if (k.getDecisionSPR().equals("approved")) {
                S = takeDistance(k, c);
                System.out.println(S);
                if (S < min) {
                    min = S;
                    offer = k;
                }
            }
        }
        System.out.println(" REQ!! Income: " + offer.getCredit().getIncome() + " Expense: " +
                offer.getCredit().getExpense() + " Sum: " + offer.getCredit().getSum() + " Term: "
                + offer.getCredit().getTerm() + " S: " + min);
        if (validKof == true && (offer.getCredit().getSum()/offer.getCredit().getTerm()) <= ((c.getCredit().getIncome() -
        c.getCredit().getExpense()))) {
            c.setDecisionSPR("approved");
            System.out.println("DecisionSPR: APPROVED");
            return;
        }
        if (validKof == true && (c.getCredit().getSum()/c.getCredit().getTerm()) <= ((c.getCredit().getIncome() -
                c.getCredit().getExpense()))) {
            c.setDecisionSPR("approved");
            System.out.println("DecisionSPR: APPROVED");
            return;
        }
        c.setDecisionSPR("failure");
        System.out.println("DecisionSPR: FAILURE");
        sprRecommend(offer,c);
    }

    public static void sprRecommend(Case offer, Case c) {
        System.out.println("Recommended parameters for credit:");
        System.out.println("Sum: " + offer.getCredit().getSum() + " Term: " + offer.getCredit().getSum()/
                ((c.getCredit().getIncome() - c.getCredit().getExpense())));
        System.out.println("Sum: " + c.getCredit().getSum() + " Term: " + c.getCredit().getSum()/
                ((c.getCredit().getIncome() - c.getCredit().getExpense())));
    }

    public static Boolean validationClient(MyFactory factory, Case c) {
        Collection<Requirements> reqs = factory.getAllRequirementsObjects();
        for(Requirements r : reqs) {
            if (takeResultValidation(factory, r, c) == false) {
                return false;
            }
        }
        System.out.println("Validation OK");
        return true;
    }

    public static Boolean takeResultValidation (MyFactory factory, Requirements r, Case c) {
        String valueString = new String(r.getValue());
        String slotString = new String();
        String slotName = new String(r.getSlotName());
        switch (slotName)  {
            case "age":
                int slotInt = c.getClient().getAge();
                int valueInt = Integer.parseInt(valueString);
                return validDouble((double) slotInt, (double) valueInt, r.getCond(), slotName);
            case "kof":
                double slotDouble = ((c.getCredit().getIncome() - c.getCredit().getExpense()) * c.getCredit().getTerm()) /
                        c.getCredit().getSum();
                double valueDouble = Double.parseDouble(r.getValue());
                Boolean result = validDouble(slotDouble, valueDouble, r.getCond(), slotName);
                if (result == false) {
                    System.out.println("Incorrect kof!");
                    takeDecision(factory, c, false);
                }
                return  result;
            case "registration":
                slotString = Boolean.toString(c.getClient().getRegistration());
                return validString(slotString, valueString, r.getCond(), slotName);
            case "validDocument":
                slotString = Boolean.toString(c.getClient().getDocument().getValid());
                return validString(slotString, valueString, r.getCond(), slotName);
            case "inBlackList":
                slotString = Boolean.toString(c.getClient().getInBlackList());
                return validString(slotString, valueString, r.getCond(), slotName);
        }
        System.out.println("ERROR! Incorrect requirements!");
        return  false;
    }

    private static Boolean validDouble(double slot, double value, String cond, String slotName) {
        System.out.println(slot + " " + cond + " " + value + " ?");
        switch (cond) {
            case "!=":
                if (slot != value) {
                    return true;
                }
                break;
            case "=":
                if (slot == value) {
                    return true;
                }
                break;
            case ">":
                if (slot > value) {
                    return true;
                }
                break;
            case "<":
                if (slot < value) {
                    return true;
                }
                break;
            case ">=":
                if (slot >= value) {
                    return true;
                }
                break;
            case "<=":
                if (slot <= value) {
                    return true;
                }
                break;
        }
        System.out.println("Incorrect " + slotName);
        return false;
    }

    private static Boolean validString(String slotString, String valueString, String cond, String slotName) {
        System.out.println(slotString + " " + cond + " " + valueString + " ?");
        switch (cond) {
            case "!=":
                if (!slotString.equals(valueString)) {
                    return true;
                }
                break;
            case "=":
                if (slotString.equals(valueString)) {
                    return  true;
                }
                break;
        }
        System.out.println("Incorrect " + slotName);
        return false;
    }

    public static double takeDistance(Case a, Case b) {
        double x = Math.pow(a.getCredit().getIncome()-b.getCredit().getIncome(), 2);
        double y = Math.pow(a.getCredit().getExpense()-b.getCredit().getExpense(), 2);
        double z = Math.pow(a.getCredit().getTerm()*1000-b.getCredit().getTerm()*1000,2);
        double w = Math.pow(a.getCredit().getSum()-b.getCredit().getSum(), 2);
        double S = Math.sqrt(x + y + z + w);

        return S/1000;
    }


}
