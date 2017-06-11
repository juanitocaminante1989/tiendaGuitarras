package info.androidhive.slidingmenu.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;

import info.androidhive.slidingmenu.entities.CategoryMessage;
import info.androidhive.slidingmenu.chops.AssociatedShops;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.entities.Client;
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


    public HashMap<Integer, CategoryMessage> consultaSubCategorias(String query) {

        HashMap<Integer, CategoryMessage> categoryMessages = new HashMap<Integer, CategoryMessage>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT codSubCat, subcategory_name FROM subCategoria WHERE codCat = '" + query + "'", null);
            //txtResultado.setText("");
            CategoryMessage categoryMessage = null;
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    categoryMessage = new CategoryMessage();
                    String codSubCat = c.getString(0);
                    String subCat = c.getString(1);

                    categoryMessage.setTitle(codSubCat);
                    categoryMessage.setMessage(subCat);

                    categoryMessages.put(i,categoryMessage);
                    i++;
                } while (c.moveToNext());
            }
        }
        return categoryMessages;
    }

    public HashMap<Integer, String> getCategoryId() {

        HashMap<Integer, String> categoryMessages = new HashMap<Integer, String>();
        categoryMessages.put(0,"");
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT codCat FROM Categoria order by codCat ASC", null);
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    String codCat = c.getString(0);

                    categoryMessages.put(i,codCat);
                    i++;
                } while (c.moveToNext());
            }
        }
        return categoryMessages;
    }

    public HashMap<Integer,String> getCategoryNames() {

        HashMap<Integer, String> categoryMessages = new HashMap<Integer, String>();
        categoryMessages.put(0,"Principal");
        categoryMessages.put(1,"Perfil");
        categoryMessages.put(2,"Búsqueda");
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT category_name FROM Categoria order by codCat ASC", null);
            int i = 3;
            if (c.moveToFirst()) {
                do {
                    String codCat = c.getString(0);

                    categoryMessages.put(i, codCat);
                    i++;
                } while (c.moveToNext());
            }
        }
        return categoryMessages;
    }

    public SparseArray<Images> getImages(String codArticulo) {
        Images image = null;
        SparseArray<Images> images =new SparseArray<Images>();
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM images WHERE codArticulo = '" + codArticulo + "'", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    image = new Images();
                    final String directory = c.getString(0);
                    final String codArticulo1 = c.getString(1);


                    image.setDirectory(directory);
                    image.setCodArticulo(codArticulo1);
                    images.put(i,image);
                    i++;
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
                    SparseArray<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);
                    productos.add(producto);
                    i++;
                } while (c.moveToNext());
            }
        }
        return productos;
    }


    public HashMap<Integer,Producto> busqueda(String buscar, Activity activity) {
        HashMap<Integer, Producto> productos = new HashMap<Integer, Producto>();
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
                        productos.put(i,producto);
                        i++;
                    } while (c.moveToNext());
                } else {
                    producto = new Producto();
                    producto.setCodArticulo("null");
                    producto.setArticulo("No se han encontrado articulos");
                    producto.setMarca("No se han encontrado articulos");
                    producto.setModelo("");
                    producto.setDescripcion("");
                    productos.put(0,producto);

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
                    SparseArray<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);
                    i++;
                } while (c.moveToNext());
            }
        }
        return producto;
    }

//    public static SparseArray<CategoryMessage> consultaSubCat(String query) {
//
//        SparseArray<CategoryMessage> categoryMessages = new SparseArray<CategoryMessage>();
//        if (Constants.database != null) {
//            Cursor c = Constants.database.rawQuery("SELECT codSubCat, subcategory_name FROM subCategoria WHERE codSubCat = '" + query + "'", null);
//            //txtResultado.setText("");
//            CategoryMessage categoryMessage = null;
//            int i = 0;
//            if (c.moveToFirst()) {
//                do {
//                    categoryMessage = new CategoryMessage();
//
//                    String codSubCat = c.getString(0);
//                    String subCat = c.getString(1);
//
//                    categoryMessage.setTitle(codSubCat);
//                    categoryMessage.setMessage(subCat);
//
//                    categoryMessages.put(i,categoryMessage);
//                    i++;
//                } while (c.moveToNext());
//            }
//        }
//        return categoryMessages;
//    }

//    public static SparseArray<AssociatedShops> getTiendas() {
//        SparseArray<AssociatedShops> tiendas = new SparseArray<AssociatedShops>();
//        if (Constants.database != null) {
//            final Cursor c = Constants.database.rawQuery("SELECT * FROM tiendas", null);
//            //txtResultado.setText("");
//
//            AssociatedShops tienda = null;
//
//            int i = 0;
//            if (c.moveToFirst()) {
//                do {
//                    tienda = new AssociatedShops();
//                    tienda.setId(Integer.parseInt(c.getString(0)));
//                    tienda.setName(c.getString(1));
//                    tienda.setCity(c.getString(2));
//                    tienda.setStreet(c.getString(3));
//                    tienda.setLongitude(Double.parseDouble(c.getString(4)));
//                    tienda.setLatitude(Double.parseDouble(c.getString(5)));
//
//                    tiendas.put(i,tienda);
//                    i++;
//                } while (c.moveToNext());
//            }
//        }
//        return tiendas;
//    }

    public static HashMap<Integer, AssociatedShops> getShopByProduct(String codArticulo) {
        HashMap<Integer, AssociatedShops> tiendas = new HashMap<Integer, AssociatedShops>();
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

                    tiendas.put(i,tienda);
                    i++;
                } while (c.moveToNext());
            }
        }
        return tiendas;
    }

    public static HashMap<Integer, ShopStock> getStockShopByProduct(String codArticulo) {
        HashMap<Integer, ShopStock> shopStocks = new HashMap<Integer, ShopStock>();
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

                    shopStocks.put(i,shopStock);
                    i++;
                } while (c.moveToNext());
            }
        }
        return shopStocks;
    }

    public static HashMap<Integer, Marca> getMarcas() {
        HashMap<Integer, Marca> marcas = new HashMap<Integer, Marca>();
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

                    marcas.put(i,marca);
                    i++;



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
                    SparseArray<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);

                    productos.add(producto);
                    i++;
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
                    SparseArray<Images> images = this.getImages(codArticulo);
                    producto.setDirectorio(images);

                    productos.add(producto);
                    i++;
                } while (c.moveToNext());
            }
        }
        return productos;
    }

//    public static SparseArray<Producto> getAllProducts(){
//        SparseArray<Producto> productos = new SparseArray<Producto>();
//        Producto producto = null;
//        if (Constants.database != null) {
//            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo", null);
//            int i = 0;
//            if (c.moveToFirst()) {
//                do {
//                    producto = new Producto();
//
//                    final String codArticulo = c.getString(0);
//                    final String codSubCat = c.getString(1);
//                    final String codCat = c.getString(2);
//                    final String articulo = c.getString(3);
//                    final String marca = c.getString(4);
//                    final int idMarca = Integer.parseInt(c.getString(5));
//                    final String modelo = c.getString(6);
//                    final String descripcion = c.getString(7);
//                    final double precio = Double.parseDouble(c.getString(8));
//                    final double IVA = Double.parseDouble(c.getString(9));
//                    final int views = Integer.parseInt(c.getString(10));
//
//                    producto.setCodArticulo(codArticulo);
//                    producto.setCodSubCat(codSubCat);
//                    producto.setCodCat(codCat);
//                    producto.setArticulo(articulo);
//                    producto.setMarca(marca);
//                    producto.setIdMarca(idMarca);
//                    producto.setModelo(modelo);
//                    producto.setDescripcion(descripcion);
//                    producto.setPrecio(precio);
//                    producto.setIVA(IVA);
//                    producto.setViews(views);
//
//                    productos.put(i,producto);
//                    i++;
//                } while (c.moveToNext());
//            }
//        }
//        return productos;
//    }

    public static boolean insertClients(Client client){
        boolean exists = false;
        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM clientes", null);
            if(c.moveToFirst()) {
                exists  = true;
                String nif = c.getString(0);
                String correo = c.getString(5);
                if (nif.equals(client.getNif())){
                    exists = true;
                }else{
                    exists = false;
                }
                if(correo.equals(client.getCorreo())) {
                    exists = true;
                }else{
                    exists = false;
                }

            }else{
                ContentValues initialValues = new ContentValues();

                initialValues.put("NIF", client.getNif());
                initialValues.put("nombre", client.getNombre());
                initialValues.put("apellidos", client.getApellidos());
                initialValues.put("direccion", client.getDireccion());
                initialValues.put("codPost", client.getCodPos());
                initialValues.put("correo", client.getCorreo());
                initialValues.put("telefono", client.getTelefono());
                initialValues.put("clave", client.getClave());
                initialValues.put("logged", 1);

                Constants.database.insert("clientes", "NIF=?", initialValues);

                exists = false;

            }
        }
//        Constants.database.insert();
                return exists;
    }

    public static Client getClient(String dni){
        Client client = null;


        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM clientes WHERE NIF = '"+dni+"'", null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    client = new Client();

                    final String nif = c.getString(0);
                    final String nombre = c.getString(1);
                    final String apellidos = c.getString(2);
                    final String direccion = c.getString(3);
                    final String codPost = c.getString(4);
                    final String correo = c.getString(5);
                    final String telefono = c.getString(6);
                    final String clave= c.getString(7);
                    final int logged = Integer.parseInt(c.getString(8));

                    client.setNif(nif);
                    client.setNombre(nombre);
                    client.setApellidos(apellidos);
                    client.setDireccion(direccion);
                    client.setCodPos(codPost);
                    client.setCorreo(correo);
                    client.setTelefono(telefono);
                    client.setClave(clave);
                    client.setLogged(logged);


                    i++;
                    Constants.currentClient= client;
                } while (c.moveToNext());
            }
        }

        return client;
    }

}
