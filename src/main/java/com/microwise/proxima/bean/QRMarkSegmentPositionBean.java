package com.microwise.proxima.bean;

/**
 * 表示 二维码标记段 在一个图片上的一次值
 * <p/>
 * Date: 12-9-28 Time: 下午3:09
 *
 * @author bastengao
 */
public class QRMarkSegmentPositionBean extends MarkSegmentPositionBean {

    // textA
    private QRCodePosition qrCodeA;

    // textB
    private QRCodePosition qrCodeB;

    public QRCodePosition getQrCodeA() {
        return qrCodeA;
    }

    public void setQrCodeA(QRCodePosition qrCodeA) {
        this.qrCodeA = qrCodeA;
    }

    public QRCodePosition getQrCodeB() {
        return qrCodeB;
    }

    public void setQrCodeB(QRCodePosition qrCodeB) {
        this.qrCodeB = qrCodeB;
    }
}
