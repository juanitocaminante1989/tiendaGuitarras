package info.androidhive.slidingmenu.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;

import java.util.ArrayList;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.constants.CategoryArrayAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.entities.Producto;

/**
 * Created by Juan on 15/06/2016.
 */
public class Controller {

    public Controller() {

    }

    public ArrayList<CategoryMessage> consultaSubCategorias(String query) {

        ArrayList<CategoryMessage> categoryMessages = new ArrayList<CategoryMessage>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT codSubCat, subCategoria FROM subCategoria WHERE codCat = '" + query + "'", null);
            //txtResultado.setText("");
            CategoryMessage categoryMessage = null;
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    categoryMessage = new CategoryMessage();
                    i = i++;
                    String codSubCat = c.getString(0);
                    String subCat = c.getString(1);

                    categoryMessage.setTitle(codSubCat);
                    categoryMessage.setMessage(subCat);

                    categoryMessages.add(categoryMessage);
                } while (c.moveToNext());
            }
        }
        return categoryMessages;
    }

    public ArrayList<CategoryMessage> consultaArticulos(String codSubCat) {

        ArrayList<CategoryMessage> categoryMessages = new ArrayList<CategoryMessage>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT articulo, codArticulo FROM articulo WHERE codSubCat = '" + codSubCat + "'", null);
            //txtResultado.setText("");


            CategoryMessage categoryMessage;
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    categoryMessage = new CategoryMessage();
                    i = i++;
                    String codArt = c.getString(0);
                    String subCat = c.getString(1);

                    categoryMessage.setTitle(codArt);
                    categoryMessage.setMessage(subCat);

                    categoryMessages.add(categoryMessage);
                } while (c.moveToNext());
            }
        }
        return categoryMessages;
    }

    public ArrayList<Producto> busqueda(String buscar, Activity activity) {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        //cuadroBusqueda = (EditText)rootView.findViewById(R.id.search);
        //String buscar= cuadroBusqueda.getText().toString();
        if ((buscar.equals("")) || (buscar == null) || (buscar.equals(" "))) {
            new AlertDialog.Builder(activity)
                    .setTitle("Campo vacío")
                    .setMessage("El campo de búsqueda no puede estar vacío")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (buscar.length() <= 2) {
            new AlertDialog.Builder(activity)
                    .setTitle("Búsqueda incompleta")
                    .setMessage("El campo de búsqueda debe tener mínimo 3 letras")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            if (Constants.database != null) {
                Cursor c = Constants.database.rawQuery("SELECT codArticulo, articulo, marca, modelo, descripcion FROM articulo WHERE articulo LIKE '%" + buscar + "%' OR marca LIKE '%" + buscar + "%' OR modelo LIKE '%" + buscar + "%'OR descripcion LIKE '%" + buscar + "%' ORDER BY articulo DESC", null);
                int i = 0;
                Producto producto = null;
                if (c.moveToFirst()) {
                    do {
                        i = i++;
                        String codArticulo = c.getString(0);
                        String articulo = c.getString(1);
                        String marca = c.getString(2);
                        String modelo = c.getString(3);
                        String descripcion = c.getString(4);

                        producto = new Producto();

                        producto.setCodArticulo(codArticulo);
                        producto.setArticulo(articulo);
                        producto.setMarca(marca);
                        producto.setModelo(modelo);
                        producto.setDescripcion(descripcion);
                        productos.add(producto);

                    } while (c.moveToNext());
                } else {
                    producto = new Producto();
                    producto.setCodArticulo("");
                    producto.setArticulo("");
                    producto.setMarca("No se han encontrado articulos");
                    producto.setModelo("");
                    producto.setDescripcion("");
                    productos.add(producto);

                }
            }
        }
        return productos;
    }

    public int getCantidadCategorias() {
        int cont = 0;
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM categoria", null);

            cont = c.getCount();
        }
        return cont;
    }
}
