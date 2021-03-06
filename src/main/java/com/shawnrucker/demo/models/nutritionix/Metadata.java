
package com.shawnrucker.demo.models.nutritionix;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "is_raw_food"
})
public class Metadata implements Serializable
{

    @JsonProperty("is_raw_food")
    private Boolean isRawFood;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -4777483964250731754L;

    @JsonProperty("is_raw_food")
    public Boolean getIsRawFood() {
        return isRawFood;
    }

    @JsonProperty("is_raw_food")
    public void setIsRawFood(Boolean isRawFood) {
        this.isRawFood = isRawFood;
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
