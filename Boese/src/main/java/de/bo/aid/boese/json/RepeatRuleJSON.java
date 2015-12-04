package de.bo.aid.boese.json;

// TODO: Auto-generated Javadoc
/**
 * The Class RepeatRuleJSON.
 */
public class RepeatRuleJSON{
    
    /** The Id. */
    private int Id;
    
    /** The temp id. */
    private int tempId;
    
    /** The repeats after end. */
    private int repeatsAfterEnd;
    
    /** The value. */
    private double value;
    
    /** The rule id. */
    private int ruleId;
    
    /** The deco id. */
    private int decoId;
    
    /** The cron. */
    private String cron;

    
    /**
     * Instantiates a new repeat rule json.
     *
     * @param id the id
     * @param repeatsAfterEnd the repeats after end
     * @param value the value
     * @param ruleId the rule id
     * @param decoId the deco id
     * @param cron the cron
     */
    public RepeatRuleJSON(int id, int repeatsAfterEnd, double value, int ruleId, int decoId, String cron) {
        super();
        Id = id;
        this.tempId = -1;
        this.repeatsAfterEnd = repeatsAfterEnd;
        this.value = value;
        this.ruleId = ruleId;
        this.decoId = decoId;
        this.cron = cron;
    }

    /**
     * Instantiates a new repeat rule json.
     *
     * @param id the id
     * @param tempId the temp id
     * @param repeatsAfterEnd the repeats after end
     * @param value the value
     * @param ruleId the rule id
     * @param decoId the deco id
     * @param cron the cron
     */
    public RepeatRuleJSON(int id, int tempId, int repeatsAfterEnd, double value, int ruleId, int decoId, String cron) {
        super();
        Id = id;
        this.tempId = tempId;
        this.repeatsAfterEnd = repeatsAfterEnd;
        this.value = value;
        this.ruleId = ruleId;
        this.decoId = decoId;
        this.cron = cron;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Gets the temp id.
     *
     * @return the temp id
     */
    public int getTempId() {
        return tempId;
    }

    /**
     * Sets the temp id.
     *
     * @param tempId the new temp id
     */
    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    /**
     * Gets the repeats after end.
     *
     * @return the repeats after end
     */
    public int getRepeatsAfterEnd() {
        return repeatsAfterEnd;
    }

    /**
     * Sets the repeats after end.
     *
     * @param repeatsAfterEnd the new repeats after end
     */
    public void setRepeatsAfterEnd(int repeatsAfterEnd) {
        this.repeatsAfterEnd = repeatsAfterEnd;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Gets the rule id.
     *
     * @return the rule id
     */
    public int getRuleId() {
        return ruleId;
    }

    /**
     * Sets the rule id.
     *
     * @param ruleId the new rule id
     */
    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * Gets the deco id.
     *
     * @return the deco id
     */
    public int getDecoId() {
        return decoId;
    }

    /**
     * Sets the deco id.
     *
     * @param decoId the new deco id
     */
    public void setDecoId(int decoId) {
        this.decoId = decoId;
    }

    /**
     * Gets the cron.
     *
     * @return the cron
     */
    public String getCron() {
        return cron;
    }

    /**
     * Sets the cron.
     *
     * @param cron the new cron
     */
    public void setCron(String cron) {
        this.cron = cron;
    }
    
    
    

}
