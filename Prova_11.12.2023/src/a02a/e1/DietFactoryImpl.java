package a02a.e1;

import java.util.Map;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return new DietImpl() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                return totalDietCalories(dietMap) >= 1500 && totalDietCalories(dietMap) <= 2000;
            }
        };
    }

    @Override
    public Diet lowCarb() {
        return new DietImpl() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                return totalDietCalories(dietMap) >= 1000 && totalDietCalories(dietMap) <= 1500 
                    && totalNutrientCalories(dietMap, Nutrient.CARBS) <= 300;
            }
            
        };
    }

    @Override
    public Diet highProtein() {
        return new DietImpl() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                return totalDietCalories(dietMap) >= 2000 && totalDietCalories(dietMap) <= 2500 
                    && totalNutrientCalories(dietMap, Nutrient.PROTEINS) >= 1300;
            }
            
        };
    }

    @Override
    public Diet balanced() {
        return new DietImpl() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                return totalDietCalories(dietMap) >= 1600 && totalDietCalories(dietMap) <= 2000 
                    && totalNutrientCalories(dietMap, Nutrient.CARBS) >= 600
                    && totalNutrientCalories(dietMap, Nutrient.PROTEINS) >= 600
                    && totalNutrientCalories(dietMap, Nutrient.FAT) >= 400
                    && totalNutrientCalories(dietMap, Nutrient.FAT)
                        + totalNutrientCalories(dietMap, Nutrient.PROTEINS) <= 1100;
            }
            
        };
    }

}
