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
        "possibleFoods"
})
public class StandardizedResult implements Serializable
{

    @JsonProperty("possibleFoods")
    private List<PossibleFood> possibleFoods = new ArrayList<PossibleFood>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6045060026922270106L;

    @JsonProperty("possibleFoods")
    public List<PossibleFood> getPossibleFoods() {
        return possibleFoods;
    }

    @JsonProperty("possibleFoods")
    public void setPossibleFoods(List<PossibleFood> possibleFoods) {
        this.possibleFoods = possibleFoods;
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