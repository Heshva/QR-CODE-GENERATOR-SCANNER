package qrcodes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

public class qr {

    public static void main(String[] args) throws WriterException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Generate QR\n2.Scan QR");
        int choice = sc.nextInt();
        sc.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Enter text:");
                String text = sc.nextLine();
                try {
                    generateQRcode(text);
                } catch (WriterException | IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                scanQRCode();  // Call method for webcam scanning
                break;

            default:
                System.out.println("Invalid Choice.");
                break;
        }
    }

    private static void generateQRcode(String text) throws WriterException, IOException {
        QRCodeWriter w = new QRCodeWriter();
        BitMatrix bitMatrix = w.encode(text, BarcodeFormat.QR_CODE, 30, 30);

        // Print QR code to terminal
        for (int y = 0; y < bitMatrix.getHeight(); y++) {
            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                System.out.print(bitMatrix.get(x, y) ? "██" : "  ");  // Unicode block for better visibility
            }
            System.out.println();
        }
    }

    private static void scanQRCode() {
        System.out.println("Starting QR Code Scanner...");

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        JFrame window = new JFrame("QR Code Scanner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        window.add(panel, BorderLayout.CENTER);
        window.setSize(800, 600);
        window.setVisible(true);

        System.out.println("Hold QR code in front of the camera...");

        new Thread(() -> {
            while (true) {
                BufferedImage image = webcam.getImage();
                if (image == null) continue;

                try {
                    String qrText = readQRCode(image);
                    if (qrText != null) {
                        System.out.println("QR Code Detected: " + qrText);
                        JOptionPane.showMessageDialog(window, "QR Code: " + qrText);
                        break;
                    }
                } catch (NotFoundException e) {
                    // No QR code found, continue scanning
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            webcam.close();
            window.dispose();
            System.out.println("Scanner closed.");
        }).start();
    }

    private static String readQRCode(BufferedImage image) throws NotFoundException {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();
    }
}
