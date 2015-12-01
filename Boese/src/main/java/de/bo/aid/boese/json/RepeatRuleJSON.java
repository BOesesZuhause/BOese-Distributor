package de.bo.aid.boese.json;

public class RepeatRuleJSON{
    
    private int Id;
    private int tempId;
    private int repeatsAfterEnd;
    private double value;
    private int ruleId;
    private int decoId;
    
    private String cron;

    


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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public int getRepeatsAfterEnd() {
        return repeatsAfterEnd;
    }

    public void setRepeatsAfterEnd(int repeatsAfterEnd) {
        this.repeatsAfterEnd = repeatsAfterEnd;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getDecoId() {
        return decoId;
    }

    public void setDecoId(int decoId) {
        this.decoId = decoId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
    
    
    

}
