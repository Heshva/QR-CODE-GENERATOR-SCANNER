# QR-CODE-GENERATOR-SCANNER
This Java application provides functionality to both generate and scan QR codes. It can:
Generate QR codes from text input and display them in the terminal
Scan QR codes using your computer's webcam and display the decoded text.

Prerequisites :
Java Development Kit (JDK) 8 or later
Maven (for dependency management)

Dependencies :
The project uses the following libraries -
ZXing ("Zebra Crossing") for QR code generation and decoding
Webcam Capture API for accessing the webcam

Installation :
Install Required Libraries-
Add these dependencies to your pom.xml if using Maven:

<dependencies>
    <!-- ZXing Core -->
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>core</artifactId>
        <version>3.4.1</version>
    </dependency>
    
    <!-- ZXing Java SE Extensions -->
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>javase</artifactId>
        <version>3.4.1</version>
    </dependency>
    
    <!-- Webcam Capture API -->
    <dependency>
        <groupId>com.github.sarxos</groupId>
        <artifactId>webcam-capture</artifactId>
        <version>0.3.12</version>
    </dependency>
</dependencies>

Or download the JAR files manually and add them to your classpath.

Webcam Drivers:
Ensure your webcam drivers are properly installed
On Linux, you might need to install v4l-utils:

Notes: 
For scanning, ensure good lighting and hold the QR code steady
The scanner will automatically close after successful detection

Troubleshooting:
If the webcam doesn't open, check your camera permissions
On Linux, you might need to run with sudo for camera access
If you get "No QR code found" messages, try adjusting the distance and angle of the QR code

