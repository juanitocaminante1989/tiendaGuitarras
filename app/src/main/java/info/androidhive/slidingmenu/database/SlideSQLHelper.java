package info.androidhive.slidingmenu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SlideSQLHelper extends SQLiteOpenHelper {

    private ArrayList<String> inserts = new ArrayList<String>();

    String sqlCreateCategoria = "CREATE TABLE categoria(codCat VARCHAR(30),category_name VARCHAR(30), descripcion VARCHAR(100), PRIMARY KEY (codCat))";
    String sqlCreateSubCategoria = "CREATE TABLE subCategoria(codSubCat VARCHAR(30),codCat VARCHAR(30),subcategory_name VARCHAR(100), descripcion VARCHAR(100),PRIMARY KEY (codSubCat),FOREIGN KEY (codCat) REFERENCES categoria(codCat))";
    String sqlCreateArticulo = "CREATE TABLE articulo(codArticulo VARCHAR(30),codSubCat VARCHAR(30),codCat VARCHAR(30),articulo_name VARCHAR(100), marca VARCHAR(30), idMarca INT(30),modelo VARCHAR(50), descripcion VARCHAR(100), precio DECIMAL(7,2),IVA DECIMAL(5,2), views INT, PRIMARY KEY (codArticulo),FOREIGN KEY (codCat) REFERENCES categoria(codCat),FOREIGN KEY (codSubCat) REFERENCES subCategoria(codSubCat))";
    String sqlCreateClientes = "CREATE TABLE clientes(NIF VARCHAR (9) NOT NULL, nombre VARCHAR(20) NOT NULL, apellidos VARCHAR(30) NOT NULL, direccion VARCHAR(40) NOT NULL,codPost VARCHAR(6) NOT NULL,correo VARCHAR(40) NOT NULL,telefono VARCHAR(9) NOT NULL,clave VARCHAR(20) NOT NULL, logged INT, CONSTRAINT correoE UNIQUE (correo),PRIMARY KEY (NIF))";
    String sqlCreateCarrito = "CREATE TABLE  Carrito(NIF VARCHAR(100), fechaCreacion DATE, fechaFin DATE, FOREIGN KEY (NIF) REFERENCES clientes(NIF) ON DELETE cascade)";
    String sqlCreateLineaCarrito = "CREATE TABLE lineaCarrito (codLinea VARCHAR(30), codArticulo VARCHAR(30), unidades INTEGER (5), NIF VARCHAR(100), PRIMARY KEY (codLinea, codArticulo), FOREIGN KEY (codArticulo) REFERENCES articulo(codArticulo), FOREIGN KEY (NIF) REFERENCES clientes(NIF) ON DELETE cascade)";
    String sqlCreateTiendas = "CREATE TABLE tiendas ( idtienda INT NOT NULL , nombre VARCHAR(50) NOT NULL , ciudad INT(50) NOT NULL , calle INT(50) NOT NULL , longitud DOUBLE(100) NOT NULL , latitud  DOUBLE(100) NOT NULL , PRIMARY KEY (idtienda))";
    String sqlCreateStock = "CREATE TABLE stock ( idStock INT NOT NULL, idtienda INT NOT NULL , codArticulo VARCHAR(50) NOT NULL , stock INT(50) NOT NULL, PRIMARY KEY (idStock))";
    String sqlCreateImages = "CREATE TABLE images ( directory VARCHAR(50) NOT NULL , codArticulo VARCHAR(30) NOT NULL, PRIMARY KEY (directory))";
    String sqlCreateMarcas = "CREATE TABLE marcas ( idMarca INT NOT NULL , nombre VARCHAR(50) NOT NULL, PRIMARY KEY (idMarca))";



    public SlideSQLHelper(Context context, String name,CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//        fillData();
//
////        for(String string: inserts){
////            db.execSQL(string);
////        }
        db.execSQL(sqlCreateCategoria);
        db.execSQL(sqlCreateSubCategoria);
        db.execSQL(sqlCreateArticulo);
        db.execSQL(sqlCreateClientes);
        db.execSQL(sqlCreateCarrito);
        db.execSQL( sqlCreateLineaCarrito);
        db.execSQL(sqlCreateTiendas);
        db.execSQL(sqlCreateStock);
        db.execSQL(sqlCreateImages);
        db.execSQL(sqlCreateMarcas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS subCategoria");
        db.execSQL("DROP TABLE IF EXISTS articulo");
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS Carrito");
        db.execSQL("DROP TABLE IF EXISTS lineaCarrito");
        db.execSQL("DROP TABLE IF EXISTS tiendas");
        db.execSQL("DROP TABLE IF EXISTS stock");
        db.execSQL("DROP TABLE IF EXISTS images");
        db.execSQL("DROP TABLE IF EXISTS marcas");

    }


    String guitar1 = "INSERT INTO categoria VALUES ('guitar1', 'Guitarras', 'Guitarras electricas, acusticas, de cualquier estilo.')";
    String bass2 = "INSERT INTO categoria VALUES ('bass2', 'Bajos', 'Bajos electricos, acusticos.')";
    String drum3 = "INSERT INTO categoria VALUES ('drum3', 'Percusion', 'Drum sets completos o separados.')";
    String keyboard4 = "INSERT INTO categoria  VALUES ('keyboard4', 'Teclados', 'Desde pianos hasta teclados.')";
    String software5 = "INSERT INTO categoria  VALUES ('software5', 'Software', 'El mejor software para grabacion y produccion.')";
    String sound6 = "INSERT INTO categoria  VALUES ('sound6', 'Sonido', 'Amplificadores y tarjetas de sonido.')";
    String audio7 = "INSERT INTO categoria  VALUES ('audio7', 'Pro Audio', 'Microfonos, auriculares, productos Line6.')";
    String computer8 = "INSERT INTO categoria  VALUES ('computer8', 'Ordenadores', 'Ordenadores optimizados para produccion musical.')";
    String wind9 = "INSERT INTO categoria  VALUES ('wind9', 'Instrumentos de Viento', 'Flautas, saxos, para dar un toque de soul.')";
    String dj10 = "INSERT INTO categoria  VALUES ('dj10', 'DJ', 'Platos, mezcladores y controladores.')";
    String access11 = "INSERT INTO categoria  VALUES ('access11', 'Accesorios', 'Cables, humbuckers, cuerdas, baquetas, etc.')";


    String access1101 = "INSERT INTO subCategoria VALUES ('access1101', 'access11', 'Cables', 'Cables para guitarra, bajos y microfonos.')";
    String access1102 = "INSERT INTO subCategoria VALUES ('access1102', 'access11', 'Humbuckers y pastillas', 'Pastillas y humbuckers para guitarras y bajos.')";
    String access1103 = "INSERT INTO subCategoria VALUES ('access1103', 'access11', 'Cuerdas', 'Cuerdas para guitarras y bajos, tanto electricos como acusticos.')";
    String access1104 = "INSERT INTO subCategoria VALUES ('access1104', 'access11', 'Baquetas', 'Baquetas de todos los grosores y tipos.')";
    String access1105 = "INSERT INTO subCategoria VALUES ('access1105', 'access11', 'Adaptadores', 'Adaptadores para cables de todos los tamaños.')";
    String access1106 = "INSERT INTO subCategoria  VALUES ('access1106', 'access11', 'Afinadores y metronomos', 'Afinadores para guitaras y bajos.')";

    String bass201 = "INSERT INTO subCategoria  VALUES ('bass201', 'bass2', 'Bajos electricos 4 cuerdas', 'Bajos electricos de 4 cuerdas de todos los modelos.')";
    String bass202 = "INSERT INTO subCategoria  VALUES ('bass202', 'bass2', 'Bajos electricos 5 cuerdas', 'Bajos electricos de 5 cuerdas de todos los modelos.')";
    String bass203 = "INSERT INTO subCategoria  VALUES ('bass203', 'bass2', 'Bajos electricos 6 cuerdas', 'Bajos electricos de 6 cuerdas de todos los modelos.')";
    String bass204 = "INSERT INTO subCategoria  VALUES ('bass204', 'bass2', 'Bajos electricos 4 cuerdas para zurdos', 'Bajos electricos para zurdos.')";
    String bass205 = "INSERT INTO subCategoria  VALUES ('bass205', 'bass2', 'Bajos acusticos 4 cuerdas', 'Bajos acusticos de todos los modelos.')";
    String bass206 = "INSERT INTO subCategoria  VALUES ('bass206', 'bass2', 'Bajos acusticos 4 cuerdas para zurdos', 'Bajos acusticos para zurdos.')";


    String dj1001 = "INSERT INTO subCategoria VALUES ('dj1001', 'dj10', 'Platos', 'Platos para mesas de mezclas.')";
    String dj1002 = "INSERT INTO subCategoria VALUES ('dj1002', 'dj10', 'Mezcladores', 'Mesas de mezcla.')";
    String dj1003 = "INSERT INTO subCategoria VALUES ('dj1003', 'dj10', 'DJTools y efectos', 'Software y plugins para mesas de mezcla.')";
    String dj1004 = "INSERT INTO subCategoria VALUES ('dj1004', 'dj10', 'Agujas y capsulas', 'Agujas y capsulas de repuesto.')";

    String guitarras101 = "INSERT INTO subCategoria  VALUES ('guitar101', 'guitar1', 'Guitarras electricas 6 cuerdas','Guitarras electricas de 6 cuerdas de todas las marcas y modelos.')";
    String guitarras102 = "INSERT INTO subCategoria  VALUES ('guitar102', 'guitar1', 'Guitarras electricas 6 cuerdas para zurdos','Guitarras electricas para zurdos.')";
    String guitarras103 = "INSERT INTO subCategoria  VALUES ('guitar103', 'guitar1', 'Guitarras electricas 7 cuerdas','Guitarras electricas de 7 cuerdas de todos los modelos.')";
    String guitarras104 = "INSERT INTO subCategoria  VALUES ('guitar104', 'guitar1', 'Guitarras electricas 8 cuerdas','Guitarras electricas de 8 cuerdas de todos los modelos.')";
    String guitarras105 = "INSERT INTO subCategoria  VALUES ('guitar105', 'guitar1', 'Guitarras acusticas 6 cuerdas','Guitarras acusticas de 6 cuerdas de todos los modelos.')";
    String guitarras106 = "INSERT INTO subCategoria  VALUES ('guitar106', 'guitar1', 'Guitarras acusticas 6 cuerdas para zurdos','Guitarras acusticas de 6 cuerdas para zurdos.')";
    String guitarras107 = "INSERT INTO subCategoria  VALUES ('guitar107', 'guitar1', 'Guitarras acusticas 12 cuerdas','Guitarras acusticas de 12 cuerdas de todos los modelos.')";
    String guitarras108 = "INSERT INTO subCategoria  VALUES ('guitar108', 'guitar1', 'Guitarras clasicas 6 cuerdas','Guitarras clasicas, españolas.')";
    String guitarras109 = "INSERT INTO subCategoria  VALUES ('guitar109', 'guitar1', 'Guitarras clasicas para zurdos','Guitarras clasicas, españolas para zurdos.')";

    String computer801 = "INSERT INTO subCategoria VALUES ('computer801', 'computer8', 'Apple.', 'Ordeandores Apple')";
    String computer802 = "INSERT INTO subCategoria VALUES ('computer802', 'computer8', 'Windows.', 'Ordenadores con Windows')";

    String drum301 = "INSERT INTO subCategoria  VALUES ('drum301', 'drum3', 'Sets completos Electricos','Drum sets completos electricos.')";
    String drum302 = "INSERT INTO subCategoria  VALUES ('drum302', 'drum3', 'Sets completos Acusticos','Drum sets completos acusticos.')";
    String drum303 = "INSERT INTO subCategoria  VALUES ('drum303', 'drum3', 'Partes sueltas','Piezas de baterias sueltas, pedales, platos, etc.')";
    String drum304 = "INSERT INTO subCategoria  VALUES ('drum304', 'drum3', 'Djembes','Djembes de todos los tamaños.')";
    String drum305 = "INSERT INTO subCategoria  VALUES ('drum305', 'drum3', 'Cajones','Cajones para efectos flamencos.');db.execSQL(computer801)";

    String audio701 = "INSERT INTO subCategoria VALUES ('audio701', 'audio7', 'Microfonos', 'Microfonos tanto para casa como para conciertos.')";
    String audio702 = "INSERT INTO subCategoria VALUES ('audio702', 'audio7', 'Auriculares', 'Auriculares para obtener la mejor experiencia musical.')";
    String audio703 = "INSERT INTO subCategoria VALUES ('audio703', 'audio7', 'Productos Line6', 'Amplificadores, pedales, tarjetas de sonido Line6.')";

    String software501 = "INSERT INTO subCategoria VALUES ('software501', 'software5', 'Secuenciadores','El mejor software para componer.')";
    String software502 = "INSERT INTO subCategoria VALUES ('software502', 'software5', 'Software instrumentos','Software para optimizar grabaciones con tus instrumentos.')";
    String software503 = "INSERT INTO subCategoria VALUES ('software503', 'software5', 'Produccion','Software de produccion y edicion, tanto musical como visual.')";

    String sound601 = "INSERT INTO subCategoria VALUES ('sound601', 'sound6', 'Amplificadores', 'Amplificadores para casa o conciertos.')";
    String sound602 = "INSERT INTO subCategoria VALUES ('sound602', 'sound6', 'Cabezales', 'Cabezales para tus amplificadores.')";
    String sound603 = "INSERT INTO subCategoria VALUES ('sound603', 'sound6', 'Altavoces', 'Altavoces tanto para casa como para conciertos.')";
    String sound604 = "INSERT INTO subCategoria VALUES ('sound604', 'sound6', 'Tarjetas de sonido', 'Tarjetas de sonido para produccion.')";

    String keyboard401 = "INSERT INTO subCategoria VALUES ('keyboard401', 'keyboard4', 'Teclados de escenario','Teclados para conciertos.')";
    String keyboard402 = "INSERT INTO subCategoria VALUES ('keyboard402', 'keyboard4', 'Teclados domesticos','Teclados para casa.')";
    String keyboard403 = "INSERT INTO subCategoria VALUES ('keyboard403', 'keyboard4', 'Pianos','Pianos de todo tipo.')";
    String keyboard404 = "INSERT INTO subCategoria VALUES ('keyboard404', 'keyboard4', 'Acordeones','Acordeones para dar un toque folk.')";

    String wind901 = "INSERT INTO subCategoria VALUES ('wind901', 'wind9', 'Flautas', 'Flautas, flautas traveseras.')";
    String wind902 = "INSERT INTO subCategoria VALUES ('wind902', 'wind9', 'Saxofones', 'Saxofones para dar mas melodia.')";
    String wind903 = "INSERT INTO subCategoria VALUES ('wind903', 'wind9', 'Trompetas', 'Trompetas de todo tipo.')";
    String wind904 = "INSERT INTO subCategoria VALUES ('wind904', 'wind9', 'Clarinetes', 'Clarinetes.')";

    String a1 = "INSERT INTO articulo  VALUES('fenderSTRdWh','guitar101', 'guitar1', 'Fender Stratocaster Red White', 'Fender', 'Stratocaster', 'Fender Stratocaster roja y blanca', 263.99, 23.3 , 'fenderstrdwh')";
    String a2 = "INSERT INTO articulo  VALUES('squireSTBlWh','guitar101', 'guitar1', 'Squire Stratocaster Blue White', 'Squire', 'Stratocaster', 'Squire Stratocaster azul y blanca', 144.99, 23.3, 'squirestblwh')";
    String a3 = "INSERT INTO articulo  VALUES('gibsonLPSb','guitar101', 'guitar1', 'Gibson Les Paul Sunburst', 'Gibson', 'Les Paul', 'Gibson Les Paul color Sunburst', 487.99, 23.3, 'gibsonlpsb')";
    String a4 = "INSERT INTO articulo  VALUES('epihoneSGBl','guitar101', 'guitar1', 'Epiphone SG Black', 'Epiphone', 'SG', 'Epiphone SG negra', 143.99, 23.3, ' epihonesgbl')";
    String a5 = "INSERT INTO articulo  VALUES('gibsonLPTrEbLf','guitar102', 'guitar1', 'Gibson Les Paul Traditional Ebony lefty', 'Gibson', 'Les Paul Traditional lefty', 'Gibson Les Paul Traditional color ebano para zurdos', 699.99, 23.3, 'gibsonlptreblf')";
    String a6 = "INSERT INTO articulo  VALUES('schekterHRBlC7','guitar103', 'guitar1', 'Schekter Hellraiser C7 Black', 'Schekter', 'Hellraiser C7', 'Schekter Hellraiser C7 color negro', 643.99, 23.3, 'schekterhrblc7')";
    String a7 = "INSERT INTO articulo  VALUES('schekterDMBlC7','guitar103', 'guitar1', 'Schekter Damien C7 Black', 'Schekter', 'Damien C7', 'Schekter Damien C7 color negro', 667.99, 23.3, 'schekterdmblc7')";
    String a8 = "INSERT INTO articulo  VALUES('schekterHRBlC8','guitar104', 'guitar1', 'Schekter Hellraiser C8 Black', 'Schekter', 'Hellraiser C8', 'Schekter Hellraiser C8 color negro', 743.99, 23.3, 'schekterhrblc8')";
    String a9 = "INSERT INTO articulo  VALUES('schekterDMBlC8','guitar104', 'guitar1', 'Schekter Damien C8 Black', 'Schekter', 'Damien C8', 'Schekter Damien C8 color negro', 767.99, 23.3, 'schekterdmblc8')";
    String a10 = "INSERT INTO articulo  VALUES('redhillRbNt','guitar105', 'guitar1', 'Red Hill Roundback Natur', 'Red Hill', 'Roundback', 'Red Hill modelo Roundback color natural', 65.99, 23.3, 'redhillrbnt')";
    String a11 = "INSERT INTO articulo  VALUES('yamahaCSBl','guitar105', 'guitar1', 'Yamaha CS Black', 'Yamaha', 'CS', 'Yamaha modelo CS color negro', 109.69, 23.3, 'yamahacsbl')";
    String a12 = "INSERT INTO articulo  VALUES('richwoodRDBlack','guitar106', 'guitar1', 'Richwood RD Lefthand Black', 'Richwood', 'RD', 'Richwood RD Zurda color negro', 99.99, 23.3, 'richwoodrdblack')";
    String a13 = "INSERT INTO articulo  VALUES('richwoodRDSb','guitar106', 'guitar1', 'Richwood RD Sunburst', 'Richwood', 'RD', 'Richwood modelo RDcolor sunburst', 119.59, 23.3, 'richwoodrdsb')";
    String a14 = "INSERT INTO articulo  VALUES('epiphoneDR12Nt','guitar107', 'guitar1', 'Epiphone DR 12 String Natur', 'Epiphone', 'DR 12 String', 'Epiphone modelo Dreadnought 12 cuerdas color natural', 122.00, 23.3, 'epiphonedr12nt')";
    String a15 = "INSERT INTO articulo  VALUES('ibanezV712Bl','guitar107', 'guitar1', 'Ibanez V7 12 String', 'Ibanez', 'V7', 'Ibanez modelo V7 12 cuerdas color negro', 197.90, 23.3, 'ibanezv712bl')";
    String a16 = "INSERT INTO articulo  VALUES('fame2a78Gr','guitar108', 'guitar1', 'Fame Modell 2a 7/8 Grosse', 'Fame', 'Modell 2a', 'Fame modelo 2a 7/8 gruesa', 187.90, 23.3, 'fame2a78gr')";
    String a17 = "INSERT INTO articulo  VALUES('almansaSe78','guitar108', 'guitar1', 'Almansa Senorita 7/8 Grosse', 'Almansa', 'Senorita', 'Almansa modelo Senorita 7/8 gruesa', 229.50, 23.3, 'almansase78')";
    String a18 = "INSERT INTO articulo  VALUES('almeriaCGBl','guitar109', 'guitar1', 'Almeria CG Black', 'Almeria', 'CG', 'Almeria modelo CG color negro', 44.50, 23.3, 'almeriacgbl')";
    String a19 = "INSERT INTO articulo  VALUES('ortegaNTNt','guitar109', 'guitar1', 'Ortega NT Natural', 'Ortega', 'NT', 'Ortega modelo NT color natural', 153.79, 23.3, 'ortegantnt')";
    String a20 = "INSERT INTO articulo  VALUES('jackdannyPBJSb','bass201', 'bass2', 'Jack and Danny PBJ Sunburst', 'Jack and Danny', 'PBJ', 'Jack and Danny PBJ color sunburst', 105.00, 23.3, 'jackdannybpjsb')";
    String a21 = "INSERT INTO articulo  VALUES('epiphoneEB0Eb','bass201', 'bass2', 'Epiphone EB0 Ebony', 'Epiphone', 'EB0', 'Epiphone EB0 color negro', 143.39, 23.3, 'epiphoneeb0eb')";
    String a22 = "INSERT INTO articulo  VALUES('peavyMlnm5Bl','bass202', 'bass2', 'Peavy Millenium 5 String Blue', 'Peavy', 'Millenium 5 String', 'Peavy Millenium 5 cuerdas azul', 262.89, 23.3, 'peavymlnm5bl')";
    String a23 = "INSERT INTO articulo  VALUES('ibanezGSRBl','bass202', 'bass2', 'Ibanez GSR 5 String Black', 'Ibanez', 'GSR', 'Ibanez GSR 5 cuerdas color negro', 322.29, 23.3, 'ibanezgsrbl')";
    String a24 = "INSERT INTO articulo  VALUES('yamahaTRB6BbNt','bass203', 'bass2', 'Yamaha TRB 6 String Bubling Natural', 'Yamaha', 'TRB', 'Yamaha TRB 6 cuerdas color bublinga natural', 3548.89, 23.3, 'yamahatrb6bbnt')";
    String a25 = "INSERT INTO articulo  VALUES('warwickPSCrvtOHV','bass203', 'bass2', 'Warwick PS Corvette 5 String Oil Honey Violin', 'Warwick', 'PS', 'Warwick PS Corvette 6 cuerdas color aceite miel', 1462.00, 23.3, 'warwickpscrvtohv')";
    String a26 = "INSERT INTO articulo  VALUES('fenderSQVinSb','bass204', 'bass2', 'Fender Squire Vintage Sunburst', 'Fender Squire', 'Vintage', 'Fender Squire Vintage color sunburt', 256.80, 23.3, 'fendersqvinsb')";
    String a27 = "INSERT INTO articulo  VALUES('ibanezSRLHBl','bass204', 'bass2', 'Ibanez SR Lefthanded Black', 'Ibanez', 'SR', 'Ibanez SR zurda color negro', 1462.00, 23.3, 'ibanezsrlhbl')";
    String a28 = "INSERT INTO articulo  VALUES('jackdannyABGNt','bass205', 'bass2', 'Jack and Danny ABG Natural', 'Jack and Danny', 'ABG', 'Jack and Danny ABG color natural', 135.20, 23.3, 'jackdannyagbnt')";
    String a29 = "INSERT INTO articulo  VALUES('ibanezAEGBVntgVi','bass205', 'bass2', 'Ibanez AEGB Vintage Violin', 'Ibanez', 'AEGB Vintage', 'Ibanez AEGB Vintage color violin', 362.99, 23.3, 'ibanezaegbvntgvi')";
    String a30 = "INSERT INTO articulo  VALUES('ortegaD14LHBl','bass206', 'bass2', 'Ortega D14 Lefthand Black', 'Ortega', 'D14 Lefthand', 'Ortega D14 zurda color negro', 642.99, 23.3, 'ortegad14lhbl')";
    String a31 = "INSERT INTO articulo  VALUES('ortegaD14LHNt','bass206', 'bass2', 'Ortega D14 Lefthand Natural', 'Ortega', 'D14 Lefthand', 'Ortega D14 zurda color natural', 572.99, 23.3, 'ortegad14lhnt')";
    String a32 = "INSERT INTO articulo  VALUES('famehd2000Cmpct','drum301', 'drum3', 'Fame HD2000 Compact EDrumset', 'Fame', 'HD2000', 'Fame HD2000 kit compacto', 252.00, 23.3, 'famehd2000cmpct')";
    String a33 = "INSERT INTO articulo  VALUES('yamahaDTX400K','drum301', 'drum3', 'Yamaha DTX400K EDrumset', 'Yamaha', 'DTX400K', 'Kit Yamaha DTX400K', 438.00, 23.3, ' yamahadtx400l')";
    String a34 = "INSERT INTO articulo  VALUES('fameBGNRSSBl','drum302', 'drum3', 'Fame Beginner Standard Set Black', 'Fame', 'Beginner Standard Set', 'Kit Principiante Fame color negro', 215.00, 23.3, 'famebgnrssbl')";
    String a35 = "INSERT INTO articulo  VALUES('fameMAStndrdS5221','drum302', 'drum3', 'Fame Maple Standard Set 5221 Black', 'Fame', 'Maple Standard Set 5221', 'Kit Fame 5221 Standard color negro', 304.70, 23.3, 'famemastndrds5221')";
    String a36 = "INSERT INTO articulo  VALUES('sabianMiniHHAA12','drum303', 'drum3', 'Sabian AA Mini HiHat 12 inch', 'Sabian', 'AA', 'HiHat Sabian AA Mini 12 pulgadas', 275.30, 23.3, 'sabianminihhaa12')";
    String a37 = "INSERT INTO articulo  VALUES('sabianAPXRide22','drum303', 'drum3', 'Sabian APX Ride 22 inch', 'Sabian', 'APX', 'Ride Sabian APX 22 pulgadas', 269.30, 23.3, 'sabianapxride22')";
    String a38 = "INSERT INTO articulo  VALUES('meinlDJWR312Br','drum304', 'drum3', 'Meinl DJWR3 12 inch Brown', 'Meinl', 'DJWR3', 'Djembe Meinl DJWR3 12 pulgadas color marron', 227.10, 23.3, 'meinldjwr312br')";
    String a39 = "INSERT INTO articulo  VALUES('tocaTFCDJ7MG7Gr','drum304', 'drum3', 'Toca Percussion TFCDJ7MG 7 inch Green', 'Toca', 'TFCDJMG7', 'Djembe Toca Percussion TFCDJMG7 7 pulgadas color verde', 28.00, 23.3, 'tocatfcdj7mg7gr')";
    String a40 = "INSERT INTO articulo  VALUES('schlagwerkCP436','drum305', 'drum3', 'Schlagwerk CP436', 'Schalgwerk', 'CP436', 'Cajon Schlagwerk modelo CP436', 200.70, 23.3, 'schlagwerkcp436')";
    String a41 = "INSERT INTO articulo  VALUES('schlagwerkCBA','drum305', 'drum3', 'Schlagwerk CBA', 'Schlagwerk', 'CBA', 'Cajon Schlagwerk modelo CBA', 269.30, 23.3, 'schlagwerkcba')";
    String a42 = "INSERT INTO articulo  VALUES('yamahaPSRS950','keyboard401', 'keyboard4', 'Yamaha PSRS950', 'Yamaha', 'PSRS950', 'Teclado Yamaha PSRS950', 1737.30, 23.3, 'yamahapsr950')";
    String a43 = "INSERT INTO articulo  VALUES('wersiPGSSWV2','keyboard401', 'keyboard4', 'Wersi Pegasus Wing V2', 'Wersi', 'Pegasus Wing V2', 'Teclado Wersi Pegasus Wing V2', 2830.00, 23.3, 'wersipgsswv2')";
    String a44 = "INSERT INTO articulo  VALUES('casioCTK100E','keyboard402', 'keyboard4', 'Casio CTK100 Einsteiger', 'Casio', 'CTK100 Einsteiger', 'Teclado CTK100 Einsteiger', 88.00, 23.3, 'casioctk100e')";
    String a45 = "INSERT INTO articulo  VALUES('rolandBK3Wh','keyboard402', 'keyboard4', 'Roland BK3 White', 'Roland', 'BK3', 'Teclado Roland BK3 color blanco', 609.00, 23.3, 'rolandbk3wh')";
    String a46 = "INSERT INTO articulo  VALUES('yamahaCLP430Bl','keyboard403', 'keyboard4', 'Yamaha CLP430 Black', 'Yamaha', 'CLP430', 'Teclado Yamaha CLP430 color negro', 1811.00, 23.3, 'yamahaclp430bl')";
    String a47 = "INSERT INTO articulo  VALUES('yamahaCLP430Br','keyboard403', 'keyboard4', 'Yamaha CLP430 Brown', 'Yamaha', 'CLP430', 'Teclado Yamaha CLP430 color marron', 1811.00, 23.3, 'yamahaclp430br')";
    String a48 = "INSERT INTO articulo  VALUES('waltherAKKT48','keyboard404', 'keyboard4', 'Walther Akkordeon Teeny 48', 'Walther', 'Teeny', 'Acordeon Walther Teeny 48 teclas', 660.49, 23.3, 'waltherakkt48')";
    String a49 = "INSERT INTO articulo  VALUES('weltmeisterMRcksk','keyboard404', 'keyboard4', 'Weltmeister Mini Rucksack Pink', 'Weltmeister', 'Mini Rucksack', 'Teclado Weltmeister Mini Rucksack color rosa', 388.00, 23.3, 'weltmeistermrcksk')";
    String a50 = "INSERT INTO articulo  VALUES('steinbergCubaseElmnts','software501', 'software5', 'Steinberg Cubase Elements', 'Steinberg Cubase', 'Elements', 'Steinberg Cubase', 93.59, 23.3, 'steinbergcubaseelmnts')";
    String a51 = "INSERT INTO articulo  VALUES('nativeInstrmntsKmplt','software501', 'software5', 'Komplete Native Instruments', 'Komplete', 'Native Instruments', 'Komplete Native Instruments', 963.90, 23.3, 'nativeinstrmntskmplt')";
    String a52 = "INSERT INTO articulo  VALUES('toontrackEZDrummrL','software502', 'software5', 'Toontrack EZ Drummer Lite', 'Toontrack', 'EZ Drummer Lite', 'Toontrack EZ Drummer Lite', 19.80, 23.3, ' toontrackezdrummrl')";
    String a53 = "INSERT INTO articulo  VALUES('musiclabRlgtr','software502', 'software5', 'MusicLab RealGuitar', 'MusicLab', 'RealGuitar', 'MusicLab RealGuitar', 151.50, 23.3, 'musiclabrlgtr')";
    String a54 = "INSERT INTO articulo  VALUES('sonyVegasPro12','software503', 'software5', 'Sony Vegas Pro 12', 'Sony', 'Vegas Pro 12', 'Sony Vegas Pro 12', 151.50, 23.3, ' sonyvegaspro12')";
    String a55 = "INSERT INTO articulo  VALUES('adobePremierePro6','software503', 'software5', 'Adobe Premiere Pro CS6', 'Adobe', 'Premiere Pro CS6', 'Adobe Premiere Pro CS6', 61.49, 23.3, 'adobepremierepro6')";
    String a56 = "INSERT INTO articulo  VALUES('marshallJMD102Cmb','sound601', 'sound6', 'Marshall JMD102 Combo', 'Marshall', 'JMD102 Combo', 'Amplificador Marshall JMD102 Combo', 990.49, 23.3, 'marshalljmd102cmb')";
    String a57 = "INSERT INTO articulo  VALUES('voxVT100Vlvtrnx212Cmb','sound601', 'sound6', 'VOX VT100 Valvetronix 212 Combo', 'VOX', 'VT100 Valvetronix 212 Combo', 'Amplificador VOX VT100 Valvetronix 212 Combo', 285.49, 23.3, 'voxvt100vlvtrnx212cmb')";
    String a58 = "INSERT INTO articulo  VALUES('hartkeHA3500','sound602', 'sound6', 'Hartke HA3500', 'Hartke', 'HA3500', 'Cabezal Hartke HA3500', 375.00, 23.3, 'hartkeha3500')";
    String a59 = "INSERT INTO articulo  VALUES('markBssMM250','sound602', 'sound6', 'Mark Bass MoMark Pure 250', 'Mark', 'MoMark Pure 250', 'Cabezal Mark Bass MoMark Pure 250', 283.90, 23.3, 'markbssmm250')";
    String a60 = "INSERT INTO articulo  VALUES('DBXDrvbckPA','sound603', 'sound6', 'DBX Driveback PA', 'DBX', 'Driveback PA', 'Sistema de control de altavoces DBX Driveback PA', 509.00, 23.3, 'dbxdrvbckpa')";
    String a61 = "INSERT INTO articulo  VALUES('electroVcDCOne','sound603', 'sound6', 'Electro Voice DCOne Digital Sound System', 'Electro', 'Voice DCOne', 'Sistema de control de altavoces Electro Voice DCOne', 777.00, 23.3, 'electrovcdcone')";
    String a62 = "INSERT INTO articulo  VALUES('rmeBOB16IO','sound604', 'sound6', 'RME BOB 16IO', 'RME', 'BOB16IO', 'Tarjeta de sonido PCI Express RME BOB 16IO', 217.20, 23.3, 'rmebob16io')";
    String a63 = "INSERT INTO articulo  VALUES('motuPCIe424Krt','sound604', 'sound6', 'Motu PCIe 424 Karte', 'Motu', '424 Karte', 'Tarjeta de sonido PCI Express Motu 424 Karte', 309.00, 23.3, 'motupcie424krt')";
    String a64 = "INSERT INTO articulo  VALUES('fameVT12','audio701', 'audio7', 'Fame VT 12', 'Fame', 'Vt12', 'Microfono de valvulas Fame VT 12',405.70, 23.3, 'famevt12')";
    String a65 = "INSERT INTO articulo  VALUES('sontronicsOmega','audio701', 'audio7', 'Sontronics Omega Rohren', 'Sontronics', 'Omega Rohren', 'Microfono de valvulas', 539.00, 23.3, 'sontronicsomega')";
    String a66 = "INSERT INTO articulo  VALUES('AKGK77','audio702', 'audio7', 'AKG K 77', 'AKG', 'K 77', 'Auriculares AKG K 77', 38.00, 23.3, 'akgk77')";
    String a67 = "INSERT INTO articulo  VALUES('pioneerHDJ500RWh','audio702', 'audio7', 'Pioneer HDJ500R White', 'Pioneer', 'HDJ500R', 'Auriculares Pioneer HDJ500R color blanco', 90.50, 23.3, 'pioneerhdj500rwh')";
    String a68 = "INSERT INTO articulo  VALUES('line6SpdrIV15','audio703', 'audio7', 'Line 6 Spider IV 15', 'Line6', 'Spider IV 15', 'Amplificador Line6 Spider IV 15', 105.00, 23.3, 'line6SpdrIV15')";
    String a69 = "INSERT INTO articulo  VALUES('line6PODStdUX2','audio703', 'audio7', 'Line6 POD Studio UX 2', 'Line6', 'POD Studio UX 2', 'Tarjeta de sonido USB Line6 POD Studio UX 2', 166.00, 23.3, 'line6podstdux2')";
    String a70 = "INSERT INTO articulo  VALUES('appleMACProQC','computer801', 'computer8', 'Apple Mac Pro 3,2 Ghz Quad Core Xeon, 6Gb Ram, 1Tb, ATI HD 5770', 'Apple', 'Mac Pro', 'Apple Mac Pro 3,2 Ghz Quad Core Xeon, 6Gb Ram, 1Tb, ATI HD 5770', 2485.10, 23.3, 'applemacproqc')";
    String a71 = "INSERT INTO articulo  VALUES('appleiMACi5','computer801', 'computer8', 'Apple iMac 27 pulgadas i5 3,2Ghz, 1Tb, 8Gb RAM', 'Apple', 'iMac', 'Apple iMac 27 pulgadas, i5 3,2Ghz, 1Tb, 8Gb RAM', 2083.40, 23.3, 'appleimaci5')";
    String a72 = "INSERT INTO articulo  VALUES('HWZ1Audio','computer802', 'computer8', 'Hewlett Packard Z1 Audio Workstation', 'Hewlett Packard', 'Z1', 'Ordenador Hewlett Packard Z1 Audio Workstation', 3049.40, 23.3, 'hwz1audio')";
    String a73 = "INSERT INTO articulo  VALUES('customMSA','computer802', 'computer8', 'Custom Music Studio Audio', 'Varios', 'Music Store Audio', 'Ordenador de sobremesa personalizado, i5 3,2Ghz, 1Tb, 8Gb RAM', 1109.40, 23.3, 'custommsa')";
    String a74 = "INSERT INTO articulo  VALUES('mollenhauer4527K','wind901', 'wind9', 'Mollenhauer 4527K', 'Mollenhauer', '4527K', 'Flauta dulce Mollenhauer 4527K', 899.00, 23.3, 'mollenhauer4527k')";
    String a75 = "INSERT INTO articulo  VALUES('yamahaYRB302','wind901', 'wind9', 'Yamaha YRB 302', 'Yamaha', 'YRB 302', 'Flauta dulce Yamaha YRB 302', 195.00, 23.3, 'yamahayrb302')";
    String a76 = "INSERT INTO articulo  VALUES('monzaniMZTS200L','wind902', 'wind9', 'Monzani MZTS 200L', 'Monzani', 'MZTS 200L', 'Saxofon Monzani MZTS 200L', 456.50, 23.3, 'monzanimzts200l')";
    String a77 = "INSERT INTO articulo  VALUES('arnoldsonsAst100','wind902', 'wind9', 'Arnold and Sons AST 100', 'Arnold and Sons', 'AST 100', 'Saxofon Arnold and Sons AST 100', 584.10, 23.3, 'arnoldsonsast100')";
    String a78 = "INSERT INTO articulo  VALUES('monzaniMZTR700L','wind903', 'wind9', 'Monzani MZTR 700L', 'Monzani', 'MZTR 700L', 'Trompeta Monzani MZTR 700L', 127.20, 23.3, 'monzanimztr700l')";
    String a79 = "INSERT INTO articulo  VALUES('yamahaYTR2435','wind903', 'wind9', 'Yamaha YTR2435', 'Yamaha', 'YTR2435', 'Trompeta Yamaha YTR2435', 568.59, 23.3, 'yamahaytr2435')";
    String a80 = "INSERT INTO articulo  VALUES('jupiterJP631','wind904', 'wind9', 'Jupiter JP631', 'Jupiter', 'JP631', 'Clarinete Jupiter JP631', 369.59, 23.3, 'jupiterjp631')";
    String a81 = "INSERT INTO articulo  VALUES('arnoldsonsBb206','wind904', 'wind9', 'Arnold and Sons Bb206', 'Arnold and Sons', 'Bb206', 'Clarinete Arnold and Sons Bb206', 575.60, 23.3, 'arnoldsonsbb206')";
    String a82 = "INSERT INTO articulo  VALUES('numarkibttlpckDJ','dj1001', 'dj10', 'Numark iBattlePack DJ', 'Numark', 'iBattlePack DJ', 'Set de platos Numark iBattlePack DJ', 304.00, 23.3, 'numarkibttlpckdj')";
    String a83 = "INSERT INTO articulo  VALUES('stageLineDJP104USB','dj1001', 'dj10', 'StageLine DJP 104USB', 'StageLine', 'DJP 104USB', 'Set de platos USB StageLine DJP 104USB', 294.99, 23.3, 'stagelinedjp104usb')";
    String a84 = "INSERT INTO articulo  VALUES('djTechiDanceZERO','dj1002', 'dj10', 'DJTech iDance ZERO', 'DJTech', 'iDance ZERO', 'Mezclador DJTech iDance ZERO con iDock', 182.00, 23.3, 'djtechidancezero')";
    String a85 = "INSERT INTO articulo  VALUES('reloopAccessII','dj1002', 'dj10', 'Reloop Access II', 'Reloop', 'Access II', 'Mezclador Reloop Access II', 98.20, 23.3, 'reloopaccessii')";
    String a86 = "INSERT INTO articulo  VALUES('korgKaossPadIII','dj1003', 'dj10', 'Korg Kaoss Pad III', 'Korg', 'Kaoss Pad III', 'Tool DJ Korg Kaoss Pad III', 288.80, 23.3, 'korgkaosspadiii')";
    String a87 = "INSERT INTO articulo  VALUES('pioneerRMX1000W','dj1003', 'dj10', 'Pioneer RMX 1000W Remix Station', 'Pioneer', 'RMX 1000W Remix Station', 'Tool DJ Pioneer RMX 1000W Remix Station', 689.00, 23.3, 'pioneerrmx1000w')";
    String a88 = "INSERT INTO articulo  VALUES('shureWHLBWhitelabel','dj1004', 'dj10', 'Shure WHLB Whitelabel', 'Shure', 'WHLB Whitelabel', 'Capsula Shure WHLB Whitelabel', 82.89, 23.3, 'shurewhlbwhitelabel')";
    String a89 = "INSERT INTO articulo  VALUES('ortofonProS','dj1004', 'dj10', 'Ortofon Pro S Scratch n Mix', 'Ortofon', 'Pro S Scratch n Mix', 'Aguja Pro S Scratch n Mix', 22.99, 23.3, 'ortofonpros')";
    String a90 = "INSERT INTO articulo  VALUES('cordialCrystal3m','access1101', 'access11', 'Cordial Crystal 3m', 'Cordial', 'Crystal 3m', 'Cable Cordial Crystal 3m', 66.10, 23.3, 'cordialcrystal3m')";
    String a91 = "INSERT INTO articulo  VALUES('cableNrm6m','access1101', 'access11', 'Normal Cable 6m', '', '', 'Cable Normal 6m', 3.40, 23.3, 'cablenrm6m')";
    String a92 = "INSERT INTO articulo  VALUES('seymourduncanSJ5SSC','access1102', 'access11', 'Seymour Duncan SJ5S Single Coil', 'Seymour Duncan', 'SJ5S Single Coil', 'Pastilla Seymour Duncan SJ5S Single Coil', 137.40, 23.3, 'seymourduncansj5ssc')";
    String a93 = "INSERT INTO articulo  VALUES('benedettoA6Hb','access1102', 'access11', 'Benedetto A6 Humbucker', 'Benedetto', 'A6 Humbucker', 'Humbucker Benedetto A6', 116.00, 23.3, 'benedettoa6hb')";
    String a94 = "INSERT INTO articulo  VALUES('deanmarkleyEgit09','access1103', 'access11', 'Dean Markley EGit 09', 'Dean Markley', 'EGit 09', 'Cuerdas de guitarra electrica Dean Markley EGit 09', 5.99, 23.3, 'deanmarkleyegit09')";
    String a95 = "INSERT INTO articulo  VALUES('savarezEgit09GS','access1103', 'access11', 'Savarez EGit 09 Gold String', 'Savarez ', 'Egit 09 Gold String', 'Cuerdas de guitarra electrica Savarez Egit 09 Gold String', 11.99, 23.3, 'savarezegit09gs')";
    String a96 = "INSERT INTO articulo  VALUES('zildjianLBHNt','access1104', 'access11', 'Zildjian Louie Bellson Hickory Natural', 'Zildjian', 'Louie Bellson Hickory', 'Baquetas Zildjian Louie Bellson Hickory madera natural', 10.90, 23.3, 'zildjianlbhnt')";
    String a97 = "INSERT INTO articulo  VALUES('zildjianTB','access1104', 'access11', 'Zildjian Travis Barker', 'Zildjian', 'Travis Barker', 'Baquetas Zildjian Travis Barker color negro', 16.40, 23.3, 'zildjiantb')";
    String a98 = "INSERT INTO articulo  VALUES('neutrikNP2CMB','access1105', 'access11', 'Neutrik NP 2 CMB', 'Neutrik', 'NP 2 CMB', 'Adaptador Neutrik NP 2 CMB', 4.19, 23.3, 'neutriknp2cmb')";
    String a99 = "INSERT INTO articulo  VALUES('ibanezRU10','access1106', 'access11', 'Ibanez RU10', 'Ibanez', 'RU10', 'Afinador de guitarra y bajo Ibanez RU10', 41.20, 23.3, 'ibanezru10')";
    String a100 = "INSERT INTO articulo  VALUES('korgBA40','access1106', 'access11', 'Korg BA40', 'Korg', 'BA40', 'Afinador de guitarra Korg BA40', 20.80, 23.3, 'korgba40')";
    public void fillData(){
        inserts.add(sqlCreateCategoria);
        inserts.add(sqlCreateSubCategoria);
        inserts.add(sqlCreateArticulo);
        inserts.add(sqlCreateClientes);
        inserts.add(sqlCreateCarrito);
        inserts.add(sqlCreateLineaCarrito);

        inserts.add(guitar1);
        inserts.add(bass2);
        inserts.add(drum3);
        inserts.add(keyboard4);
        inserts.add(software5);
        inserts.add(sound6);
        inserts.add(audio7);
        inserts.add(computer8);
        inserts.add(wind9);
        inserts.add(dj10);
        inserts.add(access11);

        inserts.add(access1101);
        inserts.add(access1102);
        inserts.add(access1103);
        inserts.add(access1104);
        inserts.add(access1105);
        inserts.add(access1106);

        inserts.add(bass201);
        inserts.add(bass202);
        inserts.add(bass203);
        inserts.add(bass204);
        inserts.add(bass205);
        inserts.add(bass206);

        inserts.add(dj1001);
        inserts.add(dj1002);
        inserts.add(dj1003);
        inserts.add(dj1004);

        inserts.add(guitarras101);
        inserts.add(guitarras102);
        inserts.add(guitarras103);
        inserts.add(guitarras104);
        inserts.add(guitarras105);
        inserts.add(guitarras106);
        inserts.add(guitarras107);
        inserts.add(guitarras108);
        inserts.add(guitarras109);

        inserts.add(computer801);
        inserts.add(computer802);

        inserts.add(drum301);
        inserts.add(drum302);
        inserts.add(drum303);
        inserts.add(drum304);
        inserts.add(drum305);

        inserts.add(audio701);
        inserts.add(audio702);
        inserts.add(audio703);

        inserts.add(software501);
        inserts.add(software502);
        inserts.add(software503);

        inserts.add(sound601);
        inserts.add(sound602);
        inserts.add(sound603);
        inserts.add(sound604);

        inserts.add(keyboard401);
        inserts.add(keyboard402);
        inserts.add(keyboard403);
        inserts.add(keyboard404);

        inserts.add(wind901);
        inserts.add(wind902);
        inserts.add(wind903);
        inserts.add(wind904);

        inserts.add(a1);
        inserts.add(a2);
        inserts.add(a3);
        inserts.add(a4);
        inserts.add(a5);
        inserts.add(a6);
        inserts.add(a7);
        inserts.add(a8);
        inserts.add(a9);
        inserts.add(a10);
        inserts.add(a11);
        inserts.add(a12);
        inserts.add(a13);
        inserts.add(a14);
        inserts.add(a15);
        inserts.add(a16);
        inserts.add(a17);
        inserts.add(a18);
        inserts.add(a19);
        inserts.add(a20);
        inserts.add(a21);
        inserts.add(a22);
        inserts.add(a23);
        inserts.add(a24);
        inserts.add(a25);
        inserts.add(a26);
        inserts.add(a27);
        inserts.add(a28);
        inserts.add(a29);
        inserts.add(a30);
        inserts.add(a31);
        inserts.add(a32);
        inserts.add(a33);
        inserts.add(a34);
        inserts.add(a35);
        inserts.add(a36);
        inserts.add(a37);
        inserts.add(a38);
        inserts.add(a39);
        inserts.add(a40);
        inserts.add(a41);
        inserts.add(a42);
        inserts.add(a43);
        inserts.add(a44);
        inserts.add(a45);
        inserts.add(a46);
        inserts.add(a47);
        inserts.add(a48);
        inserts.add(a49);
        inserts.add(a50);
        inserts.add(a51);
        inserts.add(a52);
        inserts.add(a53);
        inserts.add(a54);
        inserts.add(a55);
        inserts.add(a56);
        inserts.add(a57);
        inserts.add(a58);
        inserts.add(a59);
        inserts.add(a60);
        inserts.add(a61);
        inserts.add(a62);
        inserts.add(a63);
        inserts.add(a64);
        inserts.add(a65);
        inserts.add(a66);
        inserts.add(a67);
        inserts.add(a68);
        inserts.add(a69);
        inserts.add(a70);
        inserts.add(a71);
        inserts.add(a72);
        inserts.add(a73);
        inserts.add(a74);
        inserts.add(a75);
        inserts.add(a76);
        inserts.add(a77);
        inserts.add(a78);
        inserts.add(a79);
        inserts.add(a80);
        inserts.add(a81);
        inserts.add(a82);
        inserts.add(a83);
        inserts.add(a84);
        inserts.add(a85);
        inserts.add(a86);
        inserts.add(a87);
        inserts.add(a88);
        inserts.add(a89);
        inserts.add(a90);
        inserts.add(a91);
        inserts.add(a92);
        inserts.add(a93);
        inserts.add(a94);
        inserts.add(a95);
        inserts.add(a96);
        inserts.add(a97);
        inserts.add(a98);
        inserts.add(a99);
        inserts.add(a100);
    }
}