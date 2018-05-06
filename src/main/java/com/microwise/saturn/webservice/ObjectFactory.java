
package com.microwise.saturn.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.microwise.saturn.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _RelicData_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "RelicData");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _MaterialSummary_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "MaterialSummary");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _OwnerSummary_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "OwnerSummary");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _ArrayOfRelicData_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "ArrayOfRelicData");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _ArrayOfOwnerSummary_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "ArrayOfOwnerSummary");
    private final static QName _ArrayOfMaterialSummary_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "ArrayOfMaterialSummary");
    private final static QName _RelicDataPic2Name_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "Pic2Name");
    private final static QName _RelicDataRelicName_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "RelicName");
    private final static QName _RelicDataPic1Name_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "Pic1Name");
    private final static QName _RelicDataOwner_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "Owner");
    private final static QName _RelicDataRelicCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "RelicCode");
    private final static QName _RelicDataRelicMaterialName_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "RelicMaterialName");
    private final static QName _RelicDataRelicMaterial_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "RelicMaterial");
    private final static QName _GetListByRelicMaterialRelicMaterial_QNAME = new QName("http://tempuri.org/", "relicMaterial");
    private final static QName _GetSummaryWithRelicMaterialResponseGetSummaryWithRelicMaterialResult_QNAME = new QName("http://tempuri.org/", "GetSummaryWithRelicMaterialResult");
    private final static QName _OwnerSummaryOwnerId_QNAME = new QName("http://schemas.datacontract.org/2004/07/Wantsong.TranService", "OwnerId");
    private final static QName _GetListByOwnerOwnerId_QNAME = new QName("http://tempuri.org/", "ownerId");
    private final static QName _GetSummaryWithOwnerResponseGetSummaryWithOwnerResult_QNAME = new QName("http://tempuri.org/", "GetSummaryWithOwnerResult");
    private final static QName _GetListByOwnerResponseGetListByOwnerResult_QNAME = new QName("http://tempuri.org/", "GetListByOwnerResult");
    private final static QName _GetListByRelicMaterialResponseGetListByRelicMaterialResult_QNAME = new QName("http://tempuri.org/", "GetListByRelicMaterialResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.microwise.saturn.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetListByRelicMaterial }
     * 
     */
    public GetListByRelicMaterial createGetListByRelicMaterial() {
        return new GetListByRelicMaterial();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.RelicData }
     * 
     */
    public RelicData createRelicData() {
        return new RelicData();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetListByOwner }
     * 
     */
    public GetListByOwner createGetListByOwner() {
        return new GetListByOwner();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.ArrayOfRelicData }
     * 
     */
    public ArrayOfRelicData createArrayOfRelicData() {
        return new ArrayOfRelicData();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetSummaryWithOwnerResponse }
     * 
     */
    public GetSummaryWithOwnerResponse createGetSummaryWithOwnerResponse() {
        return new GetSummaryWithOwnerResponse();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetListByOwnerResponse }
     * 
     */
    public GetListByOwnerResponse createGetListByOwnerResponse() {
        return new GetListByOwnerResponse();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetSummaryWithRelicMaterialResponse }
     * 
     */
    public GetSummaryWithRelicMaterialResponse createGetSummaryWithRelicMaterialResponse() {
        return new GetSummaryWithRelicMaterialResponse();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.MaterialSummary }
     * 
     */
    public MaterialSummary createMaterialSummary() {
        return new MaterialSummary();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.ArrayOfMaterialSummary }
     * 
     */
    public ArrayOfMaterialSummary createArrayOfMaterialSummary() {
        return new ArrayOfMaterialSummary();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetSummaryWithRelicMaterial }
     * 
     */
    public GetSummaryWithRelicMaterial createGetSummaryWithRelicMaterial() {
        return new GetSummaryWithRelicMaterial();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.OwnerSummary }
     * 
     */
    public OwnerSummary createOwnerSummary() {
        return new OwnerSummary();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetSummaryWithOwner }
     * 
     */
    public GetSummaryWithOwner createGetSummaryWithOwner() {
        return new GetSummaryWithOwner();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.ArrayOfOwnerSummary }
     * 
     */
    public ArrayOfOwnerSummary createArrayOfOwnerSummary() {
        return new ArrayOfOwnerSummary();
    }

    /**
     * Create an instance of {@link com.microwise.saturn.webservice.GetListByRelicMaterialResponse }
     * 
     */
    public GetListByRelicMaterialResponse createGetListByRelicMaterialResponse() {
        return new GetListByRelicMaterialResponse();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.RelicData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "RelicData")
    public JAXBElement<RelicData> createRelicData(RelicData value) {
        return new JAXBElement<RelicData>(_RelicData_QNAME, RelicData.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link javax.xml.datatype.XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.MaterialSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "MaterialSummary")
    public JAXBElement<MaterialSummary> createMaterialSummary(MaterialSummary value) {
        return new JAXBElement<MaterialSummary>(_MaterialSummary_QNAME, MaterialSummary.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link javax.xml.namespace.QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.OwnerSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "OwnerSummary")
    public JAXBElement<OwnerSummary> createOwnerSummary(OwnerSummary value) {
        return new JAXBElement<OwnerSummary>(_OwnerSummary_QNAME, OwnerSummary.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfRelicData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "ArrayOfRelicData")
    public JAXBElement<ArrayOfRelicData> createArrayOfRelicData(ArrayOfRelicData value) {
        return new JAXBElement<ArrayOfRelicData>(_ArrayOfRelicData_QNAME, ArrayOfRelicData.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link java.math.BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link javax.xml.datatype.Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link java.math.BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfOwnerSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "ArrayOfOwnerSummary")
    public JAXBElement<ArrayOfOwnerSummary> createArrayOfOwnerSummary(ArrayOfOwnerSummary value) {
        return new JAXBElement<ArrayOfOwnerSummary>(_ArrayOfOwnerSummary_QNAME, ArrayOfOwnerSummary.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfMaterialSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "ArrayOfMaterialSummary")
    public JAXBElement<ArrayOfMaterialSummary> createArrayOfMaterialSummary(ArrayOfMaterialSummary value) {
        return new JAXBElement<ArrayOfMaterialSummary>(_ArrayOfMaterialSummary_QNAME, ArrayOfMaterialSummary.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "Pic2Name", scope = RelicData.class)
    public JAXBElement<String> createRelicDataPic2Name(String value) {
        return new JAXBElement<String>(_RelicDataPic2Name_QNAME, String.class, RelicData.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "RelicName", scope = RelicData.class)
    public JAXBElement<String> createRelicDataRelicName(String value) {
        return new JAXBElement<String>(_RelicDataRelicName_QNAME, String.class, RelicData.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "Pic1Name", scope = RelicData.class)
    public JAXBElement<String> createRelicDataPic1Name(String value) {
        return new JAXBElement<String>(_RelicDataPic1Name_QNAME, String.class, RelicData.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "Owner", scope = RelicData.class)
    public JAXBElement<String> createRelicDataOwner(String value) {
        return new JAXBElement<String>(_RelicDataOwner_QNAME, String.class, RelicData.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "RelicCode", scope = RelicData.class)
    public JAXBElement<String> createRelicDataRelicCode(String value) {
        return new JAXBElement<String>(_RelicDataRelicCode_QNAME, String.class, RelicData.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "RelicMaterialName", scope = RelicData.class)
    public JAXBElement<String> createRelicDataRelicMaterialName(String value) {
        return new JAXBElement<String>(_RelicDataRelicMaterialName_QNAME, String.class, RelicData.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "RelicMaterial", scope = RelicData.class)
    public JAXBElement<String> createRelicDataRelicMaterial(String value) {
        return new JAXBElement<String>(_RelicDataRelicMaterial_QNAME, String.class, RelicData.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "relicMaterial", scope = GetListByRelicMaterial.class)
    public JAXBElement<String> createGetListByRelicMaterialRelicMaterial(String value) {
        return new JAXBElement<String>(_GetListByRelicMaterialRelicMaterial_QNAME, String.class, GetListByRelicMaterial.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfMaterialSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetSummaryWithRelicMaterialResult", scope = GetSummaryWithRelicMaterialResponse.class)
    public JAXBElement<ArrayOfMaterialSummary> createGetSummaryWithRelicMaterialResponseGetSummaryWithRelicMaterialResult(ArrayOfMaterialSummary value) {
        return new JAXBElement<ArrayOfMaterialSummary>(_GetSummaryWithRelicMaterialResponseGetSummaryWithRelicMaterialResult_QNAME, ArrayOfMaterialSummary.class, GetSummaryWithRelicMaterialResponse.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "RelicMaterialName", scope = MaterialSummary.class)
    public JAXBElement<String> createMaterialSummaryRelicMaterialName(String value) {
        return new JAXBElement<String>(_RelicDataRelicMaterialName_QNAME, String.class, MaterialSummary.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "RelicMaterial", scope = MaterialSummary.class)
    public JAXBElement<String> createMaterialSummaryRelicMaterial(String value) {
        return new JAXBElement<String>(_RelicDataRelicMaterial_QNAME, String.class, MaterialSummary.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "Owner", scope = OwnerSummary.class)
    public JAXBElement<String> createOwnerSummaryOwner(String value) {
        return new JAXBElement<String>(_RelicDataOwner_QNAME, String.class, OwnerSummary.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Wantsong.TranService", name = "OwnerId", scope = OwnerSummary.class)
    public JAXBElement<String> createOwnerSummaryOwnerId(String value) {
        return new JAXBElement<String>(_OwnerSummaryOwnerId_QNAME, String.class, OwnerSummary.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ownerId", scope = GetListByOwner.class)
    public JAXBElement<String> createGetListByOwnerOwnerId(String value) {
        return new JAXBElement<String>(_GetListByOwnerOwnerId_QNAME, String.class, GetListByOwner.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfOwnerSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetSummaryWithOwnerResult", scope = GetSummaryWithOwnerResponse.class)
    public JAXBElement<ArrayOfOwnerSummary> createGetSummaryWithOwnerResponseGetSummaryWithOwnerResult(ArrayOfOwnerSummary value) {
        return new JAXBElement<ArrayOfOwnerSummary>(_GetSummaryWithOwnerResponseGetSummaryWithOwnerResult_QNAME, ArrayOfOwnerSummary.class, GetSummaryWithOwnerResponse.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfRelicData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetListByOwnerResult", scope = GetListByOwnerResponse.class)
    public JAXBElement<ArrayOfRelicData> createGetListByOwnerResponseGetListByOwnerResult(ArrayOfRelicData value) {
        return new JAXBElement<ArrayOfRelicData>(_GetListByOwnerResponseGetListByOwnerResult_QNAME, ArrayOfRelicData.class, GetListByOwnerResponse.class, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.microwise.saturn.webservice.ArrayOfRelicData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetListByRelicMaterialResult", scope = GetListByRelicMaterialResponse.class)
    public JAXBElement<ArrayOfRelicData> createGetListByRelicMaterialResponseGetListByRelicMaterialResult(ArrayOfRelicData value) {
        return new JAXBElement<ArrayOfRelicData>(_GetListByRelicMaterialResponseGetListByRelicMaterialResult_QNAME, ArrayOfRelicData.class, GetListByRelicMaterialResponse.class, value);
    }

}
