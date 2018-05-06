
package com.microwise.saturn.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelicData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelicData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Owner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pic1Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pic2Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelicCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelicMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelicMaterialName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelicName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelicData", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", propOrder = {
    "owner",
    "pic1Name",
    "pic2Name",
    "relicCode",
    "relicMaterial",
    "relicMaterialName",
    "relicName"
})
public class RelicData {

    @XmlElementRef(name = "Owner", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> owner;
    @XmlElementRef(name = "Pic1Name", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> pic1Name;
    @XmlElementRef(name = "Pic2Name", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> pic2Name;
    @XmlElementRef(name = "RelicCode", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> relicCode;
    @XmlElementRef(name = "RelicMaterial", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> relicMaterial;
    @XmlElementRef(name = "RelicMaterialName", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> relicMaterialName;
    @XmlElementRef(name = "RelicName", namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", type = JAXBElement.class)
    protected JAXBElement<String> relicName;

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOwner(JAXBElement<String> value) {
        this.owner = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pic1Name property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPic1Name() {
        return pic1Name;
    }

    /**
     * Sets the value of the pic1Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPic1Name(JAXBElement<String> value) {
        this.pic1Name = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pic2Name property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPic2Name() {
        return pic2Name;
    }

    /**
     * Sets the value of the pic2Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPic2Name(JAXBElement<String> value) {
        this.pic2Name = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the relicCode property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRelicCode() {
        return relicCode;
    }

    /**
     * Sets the value of the relicCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRelicCode(JAXBElement<String> value) {
        this.relicCode = ((JAXBElement<String> ) value);
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

    /**
     * Gets the value of the relicName property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRelicName() {
        return relicName;
    }

    /**
     * Sets the value of the relicName property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRelicName(JAXBElement<String> value) {
        this.relicName = ((JAXBElement<String> ) value);
    }

}
