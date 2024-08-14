package softwaredesign;

public class Ingredient {
    private String name;
    private Double quantity;
    private Unit unit;

    public Ingredient(String name, double quantity, Unit unit){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName(){return name;}
    public Double getQuantity(){return quantity;}
    public String getUnit() {
        if (unit == Unit.g)
            return "g";
        if (unit == Unit.mL)
            return "mL";
        if (unit == Unit.oz)
            return "oz";
        if (unit == Unit.cups)
            return "cups";
        if (unit == Unit.tbsp)
            return "tbsp";
        if (unit == Unit.tsp)
            return "tsp";
        if (unit == Unit.unit)
            return "unit";
        return "";
    }
}
