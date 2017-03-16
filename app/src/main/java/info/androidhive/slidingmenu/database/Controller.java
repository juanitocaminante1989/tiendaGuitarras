package info.androidhive.slidingmenu.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.chops.AssociatedShops;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.entities.Images;
import info.androidhive.slidingmenu.entities.Marca;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.entities.ShopStock;

/**
 * Created by Juan on 15/06/2016.
 */
public class Controller {

    public Controller() {

    }

    public static int insertOrUpdateCategory(ContentValues initialValues) {

        int id = (int) Constants.database.insertWithOnConflict("categoria", null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            Constants.database.update("categoria", initialValues, "codCat=?", new String[]{(String) initialValues.get("codCat")});
        }
        return id;
    }

    public static int insertOrUpdateSubCategory(ContentValues initialValues) {

        int id = (int) Constants.database.insertWithOnConflict("subCategoria", null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            Constants.database.update("subCategoria", initialValues, "codSubCat=?", new String[]{(String) initialValues.get("codSubCat")});
        }
        return id;
    }

    public static int insertOrUpdateProduct(ContentValues initialValues) {

        int id = (int) Constants.database.insertWithOnConflict("articulo", null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            Constants.database.update("articulo", initialValues, "codArticulo=?", new String[]{(String) initialValues.get("codArticulo")});
        }
        return id;
    }

    public static int insertOrUpdateShops(ContentValues initialValues) {

        int id = (int) Constants.database.insertWithOnConflict("tiendas", null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            Constants.database.update("tiendas", initialValues, "idtienda=?", new String[]{(String) initialValues.get("idtienda")});
        }
        return id;
    }

    public static int insertOrUpdateStock(ContentValues initialValues) {

        int id = (int) Constants.database.insertWithOnConflict("stock", null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            Constants.database.update("stock", initialValues, "idStock=?", new String[]{(String) initialValues.get("idStock")});
        }
        return id;
    }

    public static int insertOrUpdateImages(ContentValues initialValues) {

        int id = (int) Constants.database.insertWithOnConflict("images", null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            Constants.database.update("images", initialValues, "directory=?", new String[]{(String) initialValues.get("directory")});
        }
        return id;
    }

    public static int insertOrUpdateMarcas(ContentValues initialValues) {

        int id = (int) Constants.database.insertWithOnConflict("marcas", null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            Constants.database.update("marcas", initialValues, "idMarca=?", new String[]{(String) initialValues.get("idMarca")});
        }
        return id;
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
        categoryMessages.add("Perfil");
        categoryMessages.add("Búsqueda");
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

    public ArrayList<Images> getImages(String codArticulo) {
        Images image = null;
        ArrayList<Images> images =new ArrayList<Images>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM images WHERE codArticulo = '" + codArticulo + "'", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    image = new Images();
                    i = i++;
                    final String directory = c.getString(0);
                    final String codArticulo1 = c.getString(1);


                    image.setDirectory(directory);
                    image.setCodArticulo(codArticulo1);
                    images.add(image);
                } while (c.moveToNext());
            }
        }
        return images;
    }

    public ArrayList<Producto> consultaArticulos(String codArticulo) {
        Producto producto = null;
        ArrayList<Producto> productos =new ArrayList<Producto>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo WHERE codSubCat = '" + codArticulo + "'", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    producto = new Producto();
                    i = i++;
                    codArticulo = c.getString(0);
                    final String codSubCat = c.getString(1);
                    final String codCat = c.getString(2);
                    final String articulo = c.getString(3);
                    final String marca = c.getString(4);
                    final int idMarca = Integer.parseInt(c.getString(5));
                    final String modelo = c.getString(6);
                    final String descripcion = c.getString(7);
                    final double precio = Double.parseDouble(c.getString(8));
                    final double IVA = Double.parseDouble(c.getString(9));
                    final int views = Integer.parseInt(c.getString(10));


                    producto.setCodArticulo(codArticulo);
                    producto.setCodSubCat(codSubCat);
                    producto.setCodCat(codCat);
                    producto.setArticulo(articulo);
                    producto.setMarca(marca);
                    producto.setIdMarca(idMarca);
                    producto.setModelo(modelo);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(precio);
                    producto.setIVA(IVA);
                    producto.setViews(views);
                    ArrayList<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);
                    productos.add(producto);
                } while (c.moveToNext());
            }
        }
        return productos;
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
                    producto.setCodArticulo("null");
                    producto.setArticulo("No se han encontrado articulos");
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

    public Producto consulta(String codArticulo) {
        Producto producto = null;
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo WHERE codArticulo = '" + codArticulo + "'", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    producto = new Producto();
                    i = i++;
                    codArticulo = c.getString(0);
                    final String codSubCat = c.getString(1);
                    final String codCat = c.getString(2);
                    final String articulo = c.getString(3);
                    final String marca = c.getString(4);
                    final int idMarca = Integer.parseInt(c.getString(5));
                    final String modelo = c.getString(6);
                    final String descripcion = c.getString(7);
                    final double precio = Double.parseDouble(c.getString(8));
                    final double IVA = Double.parseDouble(c.getString(9));
                    final int views = Integer.parseInt(c.getString(10));

                    producto.setCodArticulo(codArticulo);
                    producto.setCodSubCat(codSubCat);
                    producto.setCodCat(codCat);
                    producto.setArticulo(articulo);
                    producto.setMarca(marca);
                    producto.setIdMarca(idMarca);
                    producto.setModelo(modelo);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(precio);
                    producto.setIVA(IVA);
                    producto.setViews(views);
                    ArrayList<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);

                } while (c.moveToNext());
            }
        }
        return producto;
    }

    public static ArrayList<CategoryMessage> consultaSubCat(String query) {

        ArrayList<CategoryMessage> categoryMessages = new ArrayList<CategoryMessage>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT codSubCat, subcategory_name FROM subCategoria WHERE codSubCat = '" + query + "'", null);
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

    public static ArrayList<AssociatedShops> getTiendas() {
        ArrayList<AssociatedShops> tiendas = new ArrayList<AssociatedShops>();
        if (Constants.database != null) {
            final Cursor c = Constants.database.rawQuery("SELECT * FROM tiendas", null);
            //txtResultado.setText("");

            AssociatedShops tienda = null;

            int i = 0;
            if (c.moveToFirst()) {
                do {
                    tienda = new AssociatedShops();
                    tienda.setId(Integer.parseInt(c.getString(0)));
                    tienda.setName(c.getString(1));
                    tienda.setCity(c.getString(2));
                    tienda.setStreet(c.getString(3));
                    tienda.setLongitude(Double.parseDouble(c.getString(4)));
                    tienda.setLatitude(Double.parseDouble(c.getString(5)));

                    i = i++;

                    tiendas.add(tienda);

                } while (c.moveToNext());
            }
        }
        return tiendas;
    }

    public static ArrayList<AssociatedShops> getShopByProduct(String codArticulo) {
        ArrayList<AssociatedShops> tiendas = new ArrayList<AssociatedShops>();
        if (Constants.database != null) {
            final Cursor c = Constants.database.rawQuery("SELECT * FROM tiendas WHERE tiendas.idtienda in (SELECT stock.idtienda from stock where stock.codArticulo = '"+codArticulo+"')", null);
            //txtResultado.setText("");

            AssociatedShops tienda = null;

            int i = 0;
            if (c.moveToFirst()) {
                do {
                    tienda = new AssociatedShops();
                    tienda.setId(Integer.parseInt(c.getString(0)));
                    tienda.setName(c.getString(1));
                    tienda.setCity(c.getString(2));
                    tienda.setStreet(c.getString(3));
                    tienda.setLongitude(Double.parseDouble(c.getString(4)));
                    tienda.setLatitude(Double.parseDouble(c.getString(5)));

                    i = i++;

                    tiendas.add(tienda);

                } while (c.moveToNext());
            }
        }
        return tiendas;
    }

    public static ArrayList<ShopStock> getStockShopByProduct(String codArticulo) {
        ArrayList<ShopStock> shopStocks = new ArrayList<ShopStock>();
        if (Constants.database != null) {
            final Cursor c = Constants.database.rawQuery("SELECT tiendas.nombre, tiendas.ciudad, tiendas.calle, stock.stock FROM tiendas, stock WHERE stock.codArticulo ='"+codArticulo+"' AND tiendas.idtienda = stock.idtienda", null);
            //txtResultado.setText("");

            ShopStock shopStock = null;

            int i = 0;
            if (c.moveToFirst()) {
                do {
                    shopStock = new ShopStock();
                    shopStock.setName(c.getString(0));
                    shopStock.setCity(c.getString(1));
                    shopStock.setStreet(c.getString(2));
                    shopStock.setStock(Integer.parseInt(c.getString(3)));

                    i = i++;

                    shopStocks.add(shopStock);

                } while (c.moveToNext());
            }
        }
        return shopStocks;
    }

    public static ArrayList<Marca> getMarcas() {
        ArrayList<Marca> marcas = new ArrayList<Marca>();
        if (Constants.database != null) {
            final Cursor c = Constants.database.rawQuery("SELECT * FROM marcas", null);
            //txtResultado.setText("");

            Marca marca = null;

            int i = 0;
            if (c.moveToFirst()) {
                do {
                    marca= new Marca();
                    marca.setIdMarca(Integer.parseInt(c.getString(0)));
                    marca.setNombre(c.getString(1));


                    i = i++;

                    marcas.add(marca);

                } while (c.moveToNext());
            }
        }
        return marcas;
    }

    public ArrayList<Producto> getProductsByMarcaId(int marcaId) {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        Producto producto = null;
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo WHERE idMarca = " + marcaId + "", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    producto = new Producto();
                    i = i++;
                    final String codArticulo = c.getString(0);
                    final String codSubCat = c.getString(1);
                    final String codCat = c.getString(2);
                    final String articulo = c.getString(3);
                    final String marca = c.getString(4);
                    final int idMarca = Integer.parseInt(c.getString(5));
                    final String modelo = c.getString(6);
                    final String descripcion = c.getString(7);
                    final double precio = Double.parseDouble(c.getString(8));
                    final double IVA = Double.parseDouble(c.getString(9));
                    final int views = Integer.parseInt(c.getString(10));

                    producto.setCodArticulo(codArticulo);
                    producto.setCodSubCat(codSubCat);
                    producto.setCodCat(codCat);
                    producto.setArticulo(articulo);
                    producto.setMarca(marca);
                    producto.setIdMarca(idMarca);
                    producto.setModelo(modelo);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(precio);
                    producto.setIVA(IVA);
                    producto.setViews(views);
                    ArrayList<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);

                    productos.add(producto);

                } while (c.moveToNext());
            }
        }
        return productos;
    }

    public static void updateViews(Producto producto){

        String cotArt = producto.getCodArticulo();
        String codCat = producto.getCodCat();
        String codSubCat = producto.getCodSubCat();
        String articulo_name = producto.getArticulo();
        String description_art = producto.getDescripcion();
        String marca_art = producto.getMarca();
        int idmarca_art = producto.getIdMarca();
        String modelo_art = producto.getModelo();
        double precio_art = producto.getPrecio();
        double iva_art = producto.getIVA();
        int views_art = producto.getViews();


        ContentValues initialValues = new ContentValues();
        initialValues.put("codArticulo", cotArt);
        initialValues.put("codSubCat", codSubCat);
        initialValues.put("codCat", codCat);
        initialValues.put("articulo_name", articulo_name);
        initialValues.put("marca", marca_art);
        initialValues.put("idMarca", idmarca_art);
        initialValues.put("modelo", modelo_art);
        initialValues.put("descripcion", description_art);
        initialValues.put("precio", precio_art);
        initialValues.put("IVA", iva_art);
        initialValues.put("views", views_art);

        Constants.database.update("articulo", initialValues, "codArticulo=?", new String[]{(String) initialValues.get("codArticulo")});

    }

    public  ArrayList<Producto> getMostViewedProducts(){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        Producto producto = null;
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo WHERE articulo.views >= 0 ORDER BY articulo.views DESC LIMIT 6", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    producto = new Producto();
                    i = i++;
                    final String codArticulo = c.getString(0);
                    final String codSubCat = c.getString(1);
                    final String codCat = c.getString(2);
                    final String articulo = c.getString(3);
                    final String marca = c.getString(4);
                    final int idMarca = Integer.parseInt(c.getString(5));
                    final String modelo = c.getString(6);
                    final String descripcion = c.getString(7);
                    final double precio = Double.parseDouble(c.getString(8));
                    final double IVA = Double.parseDouble(c.getString(9));
                    final int views = Integer.parseInt(c.getString(10));

                    producto.setCodArticulo(codArticulo);
                    producto.setCodSubCat(codSubCat);
                    producto.setCodCat(codCat);
                    producto.setArticulo(articulo);
                    producto.setMarca(marca);
                    producto.setIdMarca(idMarca);
                    producto.setModelo(modelo);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(precio);
                    producto.setIVA(IVA);
                    producto.setViews(views);
                    ArrayList<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);

                    productos.add(producto);

                } while (c.moveToNext());
            }
        }
        return productos;
    }

    public static ArrayList<Producto> getAllProducts(){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        Producto producto = null;
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    producto = new Producto();
                    i = i++;
                    final String codArticulo = c.getString(0);
                    final String codSubCat = c.getString(1);
                    final String codCat = c.getString(2);
                    final String articulo = c.getString(3);
                    final String marca = c.getString(4);
                    final int idMarca = Integer.parseInt(c.getString(5));
                    final String modelo = c.getString(6);
                    final String descripcion = c.getString(7);
                    final double precio = Double.parseDouble(c.getString(8));
                    final double IVA = Double.parseDouble(c.getString(9));
                    final int views = Integer.parseInt(c.getString(10));

                    producto.setCodArticulo(codArticulo);
                    producto.setCodSubCat(codSubCat);
                    producto.setCodCat(codCat);
                    producto.setArticulo(articulo);
                    producto.setMarca(marca);
                    producto.setIdMarca(idMarca);
                    producto.setModelo(modelo);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(precio);
                    producto.setIVA(IVA);
                    producto.setViews(views);

                    productos.add(producto);

                } while (c.moveToNext());
            }
        }
        return productos;
    }

}
