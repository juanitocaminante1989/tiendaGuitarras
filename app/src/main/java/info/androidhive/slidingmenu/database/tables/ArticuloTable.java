package info.androidhive.slidingmenu.database.tables;

/**
 * Created by Juan on 19/06/2016.
 */
public class ArticuloTable {public static final String TABLE_NAME = "articulo";

    public static final String COD_ARTICULO = "codArticulo";
    public static final String COD_CAT = "codCat";
    public static final String COD_SUB_CAT = "codSubCat";
    public static final String ARTICULO_NAME = "articulo_name";
    public static final String ARTICULO_DESCRIPTION= "descripcion";
    public static final String MARCA= "marca";
    public static final String MODELO= "modelo";
    public static final String PRECIO= "precio";
    public static final String IVA= "IVA";
    public static final String DIRECTORIO= "directorio";
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("
            +COD_ARTICULO+" TEXT, "
            +COD_SUB_CAT+" TEXT, "
            +COD_CAT+" TEXT, " +
            ARTICULO_NAME+" TEXT, " +
            MARCA +" TEXT, " +
            MODELO+" TEXT, " +
            ARTICULO_DESCRIPTION +" TEXT, " +
            PRECIO +" FLOAT, " +
            IVA +" FLOAT, " +
            DIRECTORIO +" TEXT, " +
            "PRIMARY KEY ("+COD_ARTICULO
            +"))";


    public static final String INSERT_TABLE(String codArticulo, String codSubCat, String codCat, String category_name, String marca, String modelo, String description_cat, String precio, String iva, String directorio){
        return "INSERT INTO "+TABLE_NAME+" VALUES ('"+codArticulo+"', '"+codSubCat+"', '"+codCat+"', '"+category_name+"', '"+marca+"', '"+modelo+"', '"+description_cat+"', "+precio+", "+iva+", '"+directorio+"')";
    }
}
