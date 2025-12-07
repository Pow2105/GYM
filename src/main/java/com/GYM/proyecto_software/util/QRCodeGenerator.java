package com.GYM.proyecto_software.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QrCodeGenerator {

    /**
     * Genera una imagen QR en formato PNG a partir de un texto.
     *
     * @param text El texto o ID a codificar en el QR.
     * @param width Ancho de la imagen.
     * @param height Alto de la imagen.
     * @return Arreglo de bytes que representa la imagen PNG.
     */
    public static byte[] getQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        } catch (Exception e) {
            // Manejo básico de errores: imprimir en consola y retornar null o lanzar runtime exception
            e.printStackTrace();
            return null;
        }
    }
}