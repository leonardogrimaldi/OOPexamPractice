package a02a.e1;

import java.util.HashMap;
import java.util.Map;

public abstract class DietImpl implements Diet {

    private Map<String, Map<Nutrient,Integer>> foodsNutrientMap = new HashMap<>();

    private int getNutrientCalories(String food, Nutrient n) {
        
        return foodsNutrientMap.get(food).get(n);
    }

    protected Double totalNutrientCalories(Map<String, Double> dietMap, Nutrient n) {
        return dietMap.entrySet().stream().mapToDouble(i -> (i.getValue() / 100) * getNutrientCalories(i.getKey(), n)).sum();
    }

    private int getFoodCalories(String food) {
        return foodsNutrientMap.get(food).entrySet().stream().mapToInt(i -> i.getValue()).sum();
    }

    protected Double totalDietCalories(Map<String, Double> dietMap) {
        return dietMap.entrySet().stream().mapToDouble(i -> (i.getValue() / 100 ) * getFoodCalories(i.getKey())).sum();
    }
    
    @Override
    public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
        this.foodsNutrientMap.put(name, nutritionMap);
    }

    @Override
    public abstract boolean isValid(Map<String, Double> dietMap);
}