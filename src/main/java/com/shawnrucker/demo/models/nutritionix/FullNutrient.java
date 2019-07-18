
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
    "attr_id",
    "value"
})
public class FullNutrient implements Serializable
{

    @JsonProperty("attr_id")
    private Integer attrId;
    @JsonProperty("value")
    private Double value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 467973013013351045L;

    @JsonProperty("attr_id")
    public Integer getAttrId() {
        return attrId;
    }

    @JsonProperty("attr_id")
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    @JsonProperty("value")
    public Double getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Double value) {
        this.value = value;
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
