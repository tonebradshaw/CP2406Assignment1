/**
 * Created by tony on 29/07/2016.
 * Object has all card information
 */
public class MineralCard extends Card{ //inherit name from card and add extra information

    private String formula;
    private String classification;
    private String crystalSystem;
    private String occurrence;
    private Double hardness;
    private Double specificGravity;
    private String cleavage;
    private String crustalAbundance;
    private String economicValue;

    public MineralCard(String name, String formula, String classification, String crystalSystem, String occurrence, Double hardness,
                       Double specificGravity, String cleavage, String crustalAbundance, String economicValue) { //10 attribute constructor

        super(name);
        this.formula = formula;
        this.classification = classification;
        this.crystalSystem = crystalSystem;
        this.occurrence = occurrence;
        this.hardness = hardness;
        this.specificGravity = specificGravity;
        this.cleavage = cleavage;
        this.crustalAbundance = crustalAbundance;
        this.economicValue = economicValue;
    }
    //functions to retrieve card information
    public String getFormula(){
        return formula;
    }
    public String getClassification(){
        return classification;
    }
    public String getCrystalSystem(){
        return crystalSystem;
    }
    public String getOccurrence(){
        return occurrence;
    }
    public Double getHardness(){
        return hardness;
    }
    public Double getSpecificGravity(){
        return specificGravity;
    }
    public String getCleavage(){
        return cleavage;
    }
    public String getCrustalAbundance(){
        return crustalAbundance;
    }
    public String getEconomicValue(){
        return economicValue;
    }

    @Override
    public String getName(){ return this.name;}


    public String toString() { //used to print card information

        return "Name: " + super.name + ",  " +
               "Hardness: " + hardness + ",  " +
               "Specific Gravity: " + specificGravity + ",  " +
               "Cleavage: " + cleavage + ",  " +
               "Crustal Abundance: " + crustalAbundance + ",  " +
               "Economic Value: " + economicValue + '\n';
    }

}

