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
            Cursor c = Constants.database.rawQuery("SELECT codSubCat, subcategory_name FROM subCategoria WHERE codCat = '" + query + "'", null);
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

    public ArrayList<String> getCategoryId() {

        ArrayList<String> categoryMessages = new ArrayList<String>();
        categoryMessages.add("");
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT codCat FROM Categoria order by codCat ASC", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    i = i++;
                    String codCat = c.getString(0);

                    categoryMessages.add(codCat);
                } while (c.moveToNext());
            }
        }
        return categoryMessages;
    }

    public ArrayList<String> getCategoryNames() {

        ArrayList<String> categoryMessages = new ArrayList<String>();
        categoryMessages.add("Principal");
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT category_name FROM Categoria order by codCat ASC", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    i = i++;
                    String codCat = c.getString(0);

                    categoryMessages.add(codCat);
                } while (c.moveToNext());
            }
        }
        return categoryMessages;
    }

    public ArrayList<CategoryMessage> consultaArticulos(String codSubCat) {

        ArrayList<CategoryMessage> categoryMessages = new ArrayList<CategoryMessage>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT articulo_name, codArticulo FROM articulo WHERE codSubCat = '" + codSubCat + "'", null);

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
                Cursor c = Constants.database.rawQuery("SELECT codArticulo, articulo_name, marca, modelo, descripcion FROM articulo WHERE articulo_name LIKE '%" + buscar + "%' OR marca LIKE '%" + buscar + "%' OR modelo LIKE '%" + buscar + "%'OR descripcion LIKE '%" + buscar + "%' ORDER BY articulo_name DESC", null);
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

    public ArrayList<Producto> consulta(String codArticulo) {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo WHERE codArticulo = '" + codArticulo + "'", null);
            //txtResultado.setText("");

            Producto producto = null;

            int i = 0;
            if (c.moveToFirst()) {
                do {
                    producto= new Producto();
                    i = i++;
                    codArticulo = c.getString(0);
                    final String codSubCat = c.getString(1);
                    final String codCat = c.getString(2);
                    final String articulo = c.getString(3);
                    final String marca = c.getString(4);
                    final String modelo = c.getString(5);
                    final String descripcion = c.getString(6);
                    final double precio = Double.parseDouble(c.getString(7));
                    final double IVA = Double.parseDouble(c.getString(8));
//                    final String direc = c.getString(9);
//                    int id = getResources()
//                            .getIdentifier(direc, "drawable", getActivity().getApplicationContext().getPackageName());
//                    int bla = getResources().getIdentifier("proaudio", "drawable", getActivity().getApplicationContext().getPackageName());

//                    final int directorio = id;

                    producto.setCodArticulo(codArticulo);
                    producto.setCodSubCat(codSubCat);
                    producto.setCodCat(codCat);
                    producto.setArticulo(articulo);
                    producto.setMarca(marca);
                    producto.setModelo(modelo);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(precio);
                    producto.setIVA(IVA);
                    producto.setDirectorio(-1);

                    productos.add(producto);

                } while (c.moveToNext());
            }
        }
        return  productos;
    }
}
