package com.fliqr.dev;

public interface QRCameraListener {
    void QRCodeFound(String qrCode);
    void QRCodeNotFound();
}
