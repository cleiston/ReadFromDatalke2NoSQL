/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processunstructureddata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.tool.dcm2json.Dcm2Json;
import org.bson.Document;

/**
 *
 * @author almadb
 */
public class InputStreamDecoder {
    public static Document convert2Bson(InputStream stream){
        Document doc;
        try {
            DicomInputStream dis = new DicomInputStream(stream);
            // lets get the metadata from the dicom image
                    Dcm2Json main = new Dcm2Json();
                    main.setIndent(true);
                    
                    // Redirect output stream to a ByteArrayOutputStream
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    PrintStream ps2Array = new PrintStream(baos);
                    
                    PrintStream ps2Default = System.out;
                    System.setOut(ps2Array);
                    // Begin of extracting JSON metadata
                    main.parse(dis);
                    
                    // Return to default output
                    System.out.flush();
                    System.setOut(ps2Default);
                    
                    // End of extracting JSON metadata
                    
                    // test if it worked
                    doc = Document.parse(baos.toString());
                    
                    dis.close();
        } catch (IOException e) {
            System.err.println("Problem at converting input strea: " + e.getMessage());
            doc = null;
        }
        return doc;
    }
    
    
    
    
    public static String convert2String(InputStream stream){
        String doc;
        try {
            DicomInputStream dis = new DicomInputStream(stream);
            // lets get the metadata from the dicom image
                    Dcm2Json main = new Dcm2Json();
                    main.setIndent(true);
                    
                    // Redirect output stream to a ByteArrayOutputStream
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    PrintStream ps2Array = new PrintStream(baos);
                    
                    PrintStream ps2Default = System.out;
                    System.setOut(ps2Array);
                    // Begin of extracting JSON metadata
                    main.parse(dis);
                    
                    // Return to default output
                    System.out.flush();
                    System.setOut(ps2Default);
                    
                    // End of extracting JSON metadata
                    
                    // test if it worked
                    doc = baos.toString();
                    
                    dis.close();
        } catch (IOException e) {
            System.err.println("Problem at converting input strea: " + e.getMessage());
            doc = null;
        }
        return doc;
    }
}
