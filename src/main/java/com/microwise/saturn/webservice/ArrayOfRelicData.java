
package com.microwise.saturn.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfRelicData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRelicData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RelicData" type="{http://schemas.datacontract.org/2004/07/Wantsong.TranService}RelicData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRelicData", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", propOrder = {
    "relicData"
})
public class ArrayOfRelicData {

    @XmlElement(name = "RelicData", nillable = true)
    protected List<RelicData> relicData;

    /**
     * Gets the value of the relicData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relicData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelicData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.microwise.saturn.webservice.RelicData }
     * 
     * 
     */
    public List<RelicData> getRelicData() {
        if (relicData == null) {
            relicData = new ArrayList<RelicData>();
        }
        return this.relicData;
    }

}
