package info.androidhive.slidingmenu.database.tables;

/**
 * Created by Juan on 19/06/2016.
 */
public class SubcategoriaTable {

    public static final String TABLE_NAME = "subcategoria";

    public static final String COD_CAT = "codCat";
    public static final String COD_SUB_CAT = "codSubCat";
    public static final String SUBCATEGORY_NAME = "subcategory_name";
    public static final String SUBCATEGORY_DESCRIPTION= "descripcion";
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("
            +COD_SUB_CAT+" TEXT, "
            +COD_CAT+" TEXT, " +
            SUBCATEGORY_NAME+" TEXT, " +
            SUBCATEGORY_DESCRIPTION +" TEXT, " +
            "PRIMARY KEY ("+COD_SUB_CAT
            +"))";


    public static final String INSERT_TABLE(String codSubCat, String codCat, String category_name, String description_cat){
        return "INSERT INTO "+TABLE_NAME+" VALUES ('"+codSubCat+"', '"+codCat+"', '"+category_name+"', '"+description_cat+"')";
    }
}
