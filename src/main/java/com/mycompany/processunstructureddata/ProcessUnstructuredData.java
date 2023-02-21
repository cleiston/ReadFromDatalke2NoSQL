/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.processunstructureddata;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author almadb
 */
public class ProcessUnstructuredData {

    public static void main(String[] args) {
        /*
        // Test datalake
        Datalake dl = new Datalake();
        List<String> filesInDl = dl.getItemsNameFromBucket("patientimages");
        if(filesInDl != null)
            for(String s : filesInDl)
                System.out.println(s);

        */
        /*
        // Test mongodb
        UnstructuredDB udb = new UnstructuredDB();
        List<String> res = udb.find("00080018.Value", "1.3.6.1.4.1.9590.100.1.2.422636915513941808621319796570310181528");
        System.out.println("Tamanho: " + res.get(0));
        */
        
        Datalake datalake = new Datalake();
        UnstructuredDB udb = new UnstructuredDB();
        
        List<String> itemsFromBucket = datalake.getItemsNameFromBucket("patientimages");
        if(itemsFromBucket != null){
            for(String s : itemsFromBucket){
                InputStream is = datalake.getObject("patientimages", s);
                udb.insert(InputStreamDecoder.convert2String(is), "patientimages", s);
            }
        }
        
        udb.close();
    }
}
