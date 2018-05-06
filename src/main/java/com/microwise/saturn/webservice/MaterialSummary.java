
package com.microwise.saturn.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for MaterialSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaterialSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="RelicMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelicMaterialName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaterialSummary", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", propOrder = {
    "amount",
    "relicMaterial",
    "relicMaterialName"
})
public class MaterialSummary {

    @XmlElement(name = "Amount")
    protected Integer amount;
    @XmlElementRef(name = "RelicMaterial", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> relicMaterial;
    @XmlElementRef(name = "RelicMaterialName", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> relicMaterialName;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmount(Integer value) {
        this.amount = value;
    }

    /**
     * Gets the value of the relicMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRelicMaterial() {
        return relicMaterial;
    }

    /**
     * Sets the value of the relicMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRelicMaterial(JAXBElement<String> value) {
        this.relicMaterial = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the relicMaterialName property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRelicMaterialName() {
        return relicMaterialName;
    }

    /**
     * Sets the value of the relicMaterialName property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRelicMaterialName(JAXBElement<String> value) {
        this.relicMaterialName = ((JAXBElement<String> ) value);
    }

}
