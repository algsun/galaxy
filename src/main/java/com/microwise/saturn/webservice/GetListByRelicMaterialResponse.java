
package com.microwise.saturn.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetListByRelicMaterialResult" type="{http://schemas.datacontract.org/2004/07/Wantsong.TranService}ArrayOfRelicData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getListByRelicMaterialResult"
})
@XmlRootElement(name = "GetListByRelicMaterialResponse")
public class GetListByRelicMaterialResponse {

    @XmlElementRef(name = "GetListByRelicMaterialResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfRelicData> getListByRelicMaterialResult;

    /**
     * Gets the value of the getListByRelicMaterialResult property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfRelicData }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRelicData> getGetListByRelicMaterialResult() {
        return getListByRelicMaterialResult;
    }

    /**
     * Sets the value of the getListByRelicMaterialResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfRelicData }{@code >}
     *     
     */
    public void setGetListByRelicMaterialResult(JAXBElement<ArrayOfRelicData> value) {
        this.getListByRelicMaterialResult = ((JAXBElement<ArrayOfRelicData> ) value);
    }

}
