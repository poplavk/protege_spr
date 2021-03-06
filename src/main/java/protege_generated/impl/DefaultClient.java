package protege_generated.impl;

import edu.stanford.smi.protege.code.generator.wrapping.AbstractWrappedInstance;
import edu.stanford.smi.protege.model.*;
import protege_generated.Client;
import protege_generated.*;

/**
 * Generated by Protege (http://protege.stanford.edu).
 * Source Class: Client
 *
 * @version generated on Fri Mar 25 01:08:40 MSK 2016
 */
public class DefaultClient extends AbstractWrappedInstance
         implements Client {

    public DefaultClient(Instance instance) {
        super(instance);
    }


    public DefaultClient() {
    }

    // Slot age

    public int getAge() {
        java.lang.Integer value = (java.lang.Integer) getWrappedProtegeInstance().getOwnSlotValue(getAgeSlot());
        return value == null ? null :
            (java.lang.Integer) value.intValue();
    }


    public Slot getAgeSlot() {
        final String name = "age";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasAge() {
        return hasSlotValues(getAgeSlot());
    }


    public void setAge(int newAge) {
        setSlotValue(getAgeSlot(), new java.lang.Integer(newAge));
    }

    // Slot document

    public Document getDocument() {
        Object object = getWrappedProtegeInstance().getOwnSlotValue(getDocumentSlot());
        Cls cls = getKnowledgeBase().getCls("Document");
        if (object instanceof Instance && ((Instance)object).hasType(cls)) {
            return new DefaultDocument((Instance)object);
        }
        return null;
    }


    public Slot getDocumentSlot() {
        final String name = "document";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasDocument() {
        return hasSlotValues(getDocumentSlot());
    }


    public void setDocument(Document newDocument) {
        setSlotValue(getDocumentSlot(), newDocument);
    }

    // Slot fullName

    public String getFullName() {
        return (String) getWrappedProtegeInstance().getOwnSlotValue(getFullNameSlot());
    }


    public Slot getFullNameSlot() {
        final String name = "fullName";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasFullName() {
        return hasSlotValues(getFullNameSlot());
    }


    public void setFullName(String newFullName) {
        setSlotValue(getFullNameSlot(), newFullName);
    }

    // Slot inBlackList

    public boolean getInBlackList() {
        java.lang.Boolean value = (java.lang.Boolean) getWrappedProtegeInstance().getOwnSlotValue(getInBlackListSlot());
        return value == null ? null :
            (java.lang.Boolean) value.booleanValue();
    }


    public Slot getInBlackListSlot() {
        final String name = "inBlackList";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasInBlackList() {
        return hasSlotValues(getInBlackListSlot());
    }


    public void setInBlackList(boolean newInBlackList) {
        setSlotValue(getInBlackListSlot(), new java.lang.Boolean(newInBlackList));
    }

    // Slot job

    public Job getJob() {
        Object object = getWrappedProtegeInstance().getOwnSlotValue(getJobSlot());
        Cls cls = getKnowledgeBase().getCls("Job");
        if (object instanceof Instance && ((Instance)object).hasType(cls)) {
            return new DefaultJob((Instance)object);
        }
        return null;
    }


    public Slot getJobSlot() {
        final String name = "job";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasJob() {
        return hasSlotValues(getJobSlot());
    }


    public void setJob(Job newJob) {
        setSlotValue(getJobSlot(), newJob);
    }

    // Slot phone

    public String getPhone() {
        return (String) getWrappedProtegeInstance().getOwnSlotValue(getPhoneSlot());
    }


    public Slot getPhoneSlot() {
        final String name = "phone";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasPhone() {
        return hasSlotValues(getPhoneSlot());
    }


    public void setPhone(String newPhone) {
        setSlotValue(getPhoneSlot(), newPhone);
    }

    // Slot registration

    public boolean getRegistration() {
        java.lang.Boolean value = (java.lang.Boolean) getWrappedProtegeInstance().getOwnSlotValue(getRegistrationSlot());
        return value == null ? null :
            (java.lang.Boolean) value.booleanValue();
    }


    public Slot getRegistrationSlot() {
        final String name = "registration";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasRegistration() {
        return hasSlotValues(getRegistrationSlot());
    }


    public void setRegistration(boolean newRegistration) {
        setSlotValue(getRegistrationSlot(), new java.lang.Boolean(newRegistration));
    }
}
