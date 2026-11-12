package com.fixitcampus.backend;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LeerExcel {

    public static void leerExcel() throws IOException {

        try (FileInputStream archivo = new FileInputStream("proveedores.xlsx"); XSSFWorkbook libro =
                new XSSFWorkbook(archivo);){


            Sheet hoja = libro.getSheet("proveedores");
            List<Proveedor> lst = new ArrayList<>();

            for(int i=1; i<=hoja.getLastRowNum(); i++){

                Row fila = hoja.getRow(i);
                if(fila == null){
                    continue;
                }
                String nif = fila.getCell(0).getStringCellValue();
                String razon_social = fila.getCell(1).getStringCellValue();
                String especialidad = fila.getCell(2).getStringCellValue();
                String contacto = fila.getCell(3).getStringCellValue();
                double precio_hora = fila.getCell(4).getNumericCellValue();
                lst.add(new Proveedor(nif, razon_social, especialidad, contacto, precio_hora));

            }


            System.out.println("\n--- Datos leídos del Excel ---");
            for (Proveedor proveedor : lst) {
                System.out.println(proveedor.toString());
            }

        }catch (Exception e){

            System.out.println("El archivo excel no se ha podido leer" + " " + e.getMessage());

        }
    }
}
