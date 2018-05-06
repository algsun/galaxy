
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
 *         &lt;element name="GetSummaryWithOwnerResult" type="{http://schemas.datacontract.org/2004/07/Wantsong.TranService}ArrayOfOwnerSummary" minOccurs="0"/>
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
    "getSummaryWithOwnerResult"
})
@XmlRootElement(name = "GetSummaryWithOwnerResponse")
public class GetSummaryWithOwnerResponse {

    @XmlElementRef(name = "GetSummaryWithOwnerResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfOwnerSummary> getSummaryWithOwnerResult;

    /**
     * Gets the value of the getSummaryWithOwnerResult property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfOwnerSummary }{@code >}
     *     
     */
    public JAXBElement<ArrayOfOwnerSummary> getGetSummaryWithOwnerResult() {
        return getSummaryWithOwnerResult;
    }

    /**
     * Sets the value of the getSummaryWithOwnerResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfOwnerSummary }{@code >}
     *     
     */
    public void setGetSummaryWithOwnerResult(JAXBElement<ArrayOfOwnerSummary> value) {
        this.getSummaryWithOwnerResult = ((JAXBElement<ArrayOfOwnerSummary> ) value);
    }

}
