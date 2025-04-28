package Dao;

import java.sql.ResultSet;
public interface CrudDao {
    public int Ajouter(Object object);
    public int Modifier(Object object);
    public int Supprimer(int id);
    public ResultSet AfficherTout();
    public ResultSet Rechercher(int id);

}
