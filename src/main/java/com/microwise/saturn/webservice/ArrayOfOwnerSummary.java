
package com.microwise.saturn.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfOwnerSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOwnerSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OwnerSummary" type="{http://schemas.datacontract.org/2004/07/Wantsong.TranService}OwnerSummary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOwnerSummary", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", propOrder = {
    "ownerSummary"
})
public class ArrayOfOwnerSummary {

    @XmlElement(name = "OwnerSummary", nillable = true)
    protected List<OwnerSummary> ownerSummary;

    /**
     * Gets the value of the ownerSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownerSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnerSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.microwise.saturn.webservice.OwnerSummary }
     * 
     * 
     */
    public List<OwnerSummary> getOwnerSummary() {
        if (ownerSummary == null) {
            ownerSummary = new ArrayList<OwnerSummary>();
        }
        return this.ownerSummary;
    }

}
