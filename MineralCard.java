/**
 * Created by tony on 29/07/2016.
 * Object has all card information
 */
public class MineralCard extends Card{ //card information

    private String formula;
    private String classification;
    private String crystalSystem;
    private String occurrence;
    private Double hardness;
    private Double specificGravity;
    private String cleavage;
    private String crustalAbundance;
    private String economicValue;

    public MineralCard(){ //default constructor

    }
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
    public String toString() { //used to print card values

        return "Name: " + this.getName() + '\n' +
               "Formula: " + this.getFormula() + '\n' +
               "Classification: " + this.getClassification() + '\n' +
               "Crystal System: " + this.getCrystalSystem() + '\n' +
               "Occurrence: " + this.getOccurrence() + '\n' +
               "Hardness: " + this.getHardness() + '\n' +
               "Specific Gravity: " + this.getSpecificGravity() + '\n' +
               "Cleavage: " + this.getCleavage() + '\n' +
               "Crustal Abundance: " + this.getCrustalAbundance() + '\n' +
               "Economic Value: " + this.getEconomicValue() + '\n';
    }

}

