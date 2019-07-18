
package com.shawnrucker.demo.models.nutritionix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "food_name",
    "brand_name",
    "serving_qty",
    "serving_unit",
    "serving_weight_grams",
    "nf_calories",
    "nf_total_fat",
    "nf_saturated_fat",
    "nf_cholesterol",
    "nf_sodium",
    "nf_total_carbohydrate",
    "nf_dietary_fiber",
    "nf_sugars",
    "nf_protein",
    "nf_potassium",
    "nf_p",
    "full_nutrients",
    "nix_brand_name",
    "nix_brand_id",
    "nix_item_name",
    "nix_item_id",
    "upc",
    "consumed_at",
    "metadata",
    "source",
    "ndb_no",
    "tags",
    "alt_measures",
    "lat",
    "lng",
    "meal_type",
    "photo",
    "sub_recipe"
})
public class Food implements Serializable
{
    @JsonProperty("food_name")
    private String foodName;
    @JsonProperty("brand_name")
    private Object brandName;
    @JsonProperty("serving_qty")
    private Integer servingQty;
    @JsonProperty("serving_unit")
    private String servingUnit;
    @JsonProperty("serving_weight_grams")
    private Integer servingWeightGrams;
    @JsonProperty("nf_calories")
    private Double nfCalories;
    @JsonProperty("nf_total_fat")
    private Double nfTotalFat;
    @JsonProperty("nf_saturated_fat")
    private Double nfSaturatedFat;
    @JsonProperty("nf_cholesterol")
    private Integer nfCholesterol;
    @JsonProperty("nf_sodium")
    private Double nfSodium;
    @JsonProperty("nf_total_carbohydrate")
    private Double nfTotalCarbohydrate;
    @JsonProperty("nf_dietary_fiber")
    private Double nfDietaryFiber;
    @JsonProperty("nf_sugars")
    private Double nfSugars;
    @JsonProperty("nf_protein")
    private Double nfProtein;
    @JsonProperty("nf_potassium")
    private Double nfPotassium;
    @JsonProperty("nf_p")
    private Double nfP;
    @JsonProperty("full_nutrients")
    private List<FullNutrient> fullNutrients = new ArrayList<FullNutrient>();
    @JsonProperty("nix_brand_name")
    private Object nixBrandName;
    @JsonProperty("nix_brand_id")
    private Object nixBrandId;
    @JsonProperty("nix_item_name")
    private Object nixItemName;
    @JsonProperty("nix_item_id")
    private Object nixItemId;
    @JsonProperty("upc")
    private Object upc;
    @JsonProperty("consumed_at")
    private String consumedAt;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("source")
    private Integer source;
    @JsonProperty("ndb_no")
    private Integer ndbNo;
    @JsonProperty("tags")
    private Tags tags;
    @JsonProperty("alt_measures")
    private List<AltMeasure> altMeasures = new ArrayList<AltMeasure>();
    @JsonProperty("lat")
    private Object lat;
    @JsonProperty("lng")
    private Object lng;
    @JsonProperty("meal_type")
    private Integer mealType;
    @JsonProperty("photo")
    private Photo photo;
    @JsonProperty("sub_recipe")
    private Object subRecipe;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 440483708450741700L;

    @JsonProperty("food_name")
    public String getFoodName() {
        return foodName;
    }

    @JsonProperty("food_name")
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    @JsonProperty("brand_name")
    public Object getBrandName() {
        return brandName;
    }

    @JsonProperty("brand_name")
    public void setBrandName(Object brandName) {
        this.brandName = brandName;
    }

    @JsonProperty("serving_qty")
    public Integer getServingQty() {
        return servingQty;
    }

    @JsonProperty("serving_qty")
    public void setServingQty(Integer servingQty) {
        this.servingQty = servingQty;
    }

    @JsonProperty("serving_unit")
    public String getServingUnit() {
        return servingUnit;
    }

    @JsonProperty("serving_unit")
    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    @JsonProperty("serving_weight_grams")
    public Integer getServingWeightGrams() {
        return servingWeightGrams;
    }

    @JsonProperty("serving_weight_grams")
    public void setServingWeightGrams(Integer servingWeightGrams) {
        this.servingWeightGrams = servingWeightGrams;
    }

    @JsonProperty("nf_calories")
    public Double getNfCalories() {
        return nfCalories;
    }

    @JsonProperty("nf_calories")
    public void setNfCalories(Double nfCalories) {
        this.nfCalories = nfCalories;
    }

    @JsonProperty("nf_total_fat")
    public Double getNfTotalFat() {
        return nfTotalFat;
    }

    @JsonProperty("nf_total_fat")
    public void setNfTotalFat(Double nfTotalFat) {
        this.nfTotalFat = nfTotalFat;
    }

    @JsonProperty("nf_saturated_fat")
    public Double getNfSaturatedFat() {
        return nfSaturatedFat;
    }

    @JsonProperty("nf_saturated_fat")
    public void setNfSaturatedFat(Double nfSaturatedFat) {
        this.nfSaturatedFat = nfSaturatedFat;
    }

    @JsonProperty("nf_cholesterol")
    public Integer getNfCholesterol() {
        return nfCholesterol;
    }

    @JsonProperty("nf_cholesterol")
    public void setNfCholesterol(Integer nfCholesterol) {
        this.nfCholesterol = nfCholesterol;
    }

    @JsonProperty("nf_sodium")
    public Double getNfSodium() {
        return nfSodium;
    }

    @JsonProperty("nf_sodium")
    public void setNfSodium(Double nfSodium) {
        this.nfSodium = nfSodium;
    }

    @JsonProperty("nf_total_carbohydrate")
    public Double getNfTotalCarbohydrate() {
        return nfTotalCarbohydrate;
    }

    @JsonProperty("nf_total_carbohydrate")
    public void setNfTotalCarbohydrate(Double nfTotalCarbohydrate) {
        this.nfTotalCarbohydrate = nfTotalCarbohydrate;
    }

    @JsonProperty("nf_dietary_fiber")
    public Double getNfDietaryFiber() {
        return nfDietaryFiber;
    }

    @JsonProperty("nf_dietary_fiber")
    public void setNfDietaryFiber(Double nfDietaryFiber) {
        this.nfDietaryFiber = nfDietaryFiber;
    }

    @JsonProperty("nf_sugars")
    public Double getNfSugars() {
        return nfSugars;
    }

    @JsonProperty("nf_sugars")
    public void setNfSugars(Double nfSugars) {
        this.nfSugars = nfSugars;
    }

    @JsonProperty("nf_protein")
    public Double getNfProtein() {
        return nfProtein;
    }

    @JsonProperty("nf_protein")
    public void setNfProtein(Double nfProtein) {
        this.nfProtein = nfProtein;
    }

    @JsonProperty("nf_potassium")
    public Double getNfPotassium() {
        return nfPotassium;
    }

    @JsonProperty("nf_potassium")
    public void setNfPotassium(Double nfPotassium) {
        this.nfPotassium = nfPotassium;
    }

    @JsonProperty("nf_p")
    public Double getNfP() {
        return nfP;
    }

    @JsonProperty("nf_p")
    public void setNfP(Double nfP) {
        this.nfP = nfP;
    }

    @JsonProperty("full_nutrients")
    public List<FullNutrient> getFullNutrients() {
        return fullNutrients;
    }

    @JsonProperty("full_nutrients")
    public void setFullNutrients(List<FullNutrient> fullNutrients) {
        this.fullNutrients = fullNutrients;
    }

    @JsonProperty("nix_brand_name")
    public Object getNixBrandName() {
        return nixBrandName;
    }

    @JsonProperty("nix_brand_name")
    public void setNixBrandName(Object nixBrandName) {
        this.nixBrandName = nixBrandName;
    }

    @JsonProperty("nix_brand_id")
    public Object getNixBrandId() {
        return nixBrandId;
    }

    @JsonProperty("nix_brand_id")
    public void setNixBrandId(Object nixBrandId) {
        this.nixBrandId = nixBrandId;
    }

    @JsonProperty("nix_item_name")
    public Object getNixItemName() {
        return nixItemName;
    }

    @JsonProperty("nix_item_name")
    public void setNixItemName(Object nixItemName) {
        this.nixItemName = nixItemName;
    }

    @JsonProperty("nix_item_id")
    public Object getNixItemId() {
        return nixItemId;
    }

    @JsonProperty("nix_item_id")
    public void setNixItemId(Object nixItemId) {
        this.nixItemId = nixItemId;
    }

    @JsonProperty("upc")
    public Object getUpc() {
        return upc;
    }

    @JsonProperty("upc")
    public void setUpc(Object upc) {
        this.upc = upc;
    }

    @JsonProperty("consumed_at")
    public String getConsumedAt() {
        return consumedAt;
    }

    @JsonProperty("consumed_at")
    public void setConsumedAt(String consumedAt) {
        this.consumedAt = consumedAt;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("source")
    public Integer getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(Integer source) {
        this.source = source;
    }

    @JsonProperty("ndb_no")
    public Integer getNdbNo() {
        return ndbNo;
    }

    @JsonProperty("ndb_no")
    public void setNdbNo(Integer ndbNo) {
        this.ndbNo = ndbNo;
    }

    @JsonProperty("tags")
    public Tags getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(Tags tags) {
        this.tags = tags;
    }

    @JsonProperty("alt_measures")
    public List<AltMeasure> getAltMeasures() {
        return altMeasures;
    }

    @JsonProperty("alt_measures")
    public void setAltMeasures(List<AltMeasure> altMeasures) {
        this.altMeasures = altMeasures;
    }

    @JsonProperty("lat")
    public Object getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Object lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public Object getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Object lng) {
        this.lng = lng;
    }

    @JsonProperty("meal_type")
    public Integer getMealType() {
        return mealType;
    }

    @JsonProperty("meal_type")
    public void setMealType(Integer mealType) {
        this.mealType = mealType;
    }

    @JsonProperty("photo")
    public Photo getPhoto() {
        return photo;
    }

    @JsonProperty("photo")
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @JsonProperty("sub_recipe")
    public Object getSubRecipe() {
        return subRecipe;
    }

    @JsonProperty("sub_recipe")
    public void setSubRecipe(Object subRecipe) {
        this.subRecipe = subRecipe;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
