
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
 *         &lt;element name="GetSummaryWithRelicMaterialResult" type="{http://schemas.datacontract.org/2004/07/Wantsong.TranService}ArrayOfMaterialSummary" minOccurs="0"/>
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
    "getSummaryWithRelicMaterialResult"
})
@XmlRootElement(name = "GetSummaryWithRelicMaterialResponse")
public class GetSummaryWithRelicMaterialResponse {

    @XmlElementRef(name = "GetSummaryWithRelicMaterialResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfMaterialSummary> getSummaryWithRelicMaterialResult;

    /**
     * Gets the value of the getSummaryWithRelicMaterialResult property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfMaterialSummary }{@code >}
     *     
     */
    public JAXBElement<ArrayOfMaterialSummary> getGetSummaryWithRelicMaterialResult() {
        return getSummaryWithRelicMaterialResult;
    }

    /**
     * Sets the value of the getSummaryWithRelicMaterialResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfMaterialSummary }{@code >}
     *     
     */
    public void setGetSummaryWithRelicMaterialResult(JAXBElement<ArrayOfMaterialSummary> value) {
        this.getSummaryWithRelicMaterialResult = ((JAXBElement<ArrayOfMaterialSummary> ) value);
    }

}
