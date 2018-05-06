
package com.microwise.saturn.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfMaterialSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMaterialSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaterialSummary" type="{http://schemas.datacontract.org/2004/07/Wantsong.TranService}MaterialSummary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMaterialSummary", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", propOrder = {
    "materialSummary"
})
public class ArrayOfMaterialSummary {

    @XmlElement(name = "MaterialSummary", nillable = true)
    protected List<MaterialSummary> materialSummary;

    /**
     * Gets the value of the materialSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the materialSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaterialSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.microwise.saturn.webservice.MaterialSummary }
     * 
     * 
     */
    public List<MaterialSummary> getMaterialSummary() {
        if (materialSummary == null) {
            materialSummary = new ArrayList<MaterialSummary>();
        }
        return this.materialSummary;
    }

}
