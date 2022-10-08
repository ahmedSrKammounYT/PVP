/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package MySQL;

import MySQL.MySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.entity.Player;
import src.main;

public class Kills {
    public static void createTable() {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS kills (NAME VARCHAR(100),UUID VARCHAR(100),KILLS INT(100),PRIMARY KEY (NAME))");
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createPlayer(Player p) {
        try {
            UUID uuid = p.getUniqueId();
            if (!Kills.Playerexists(uuid)) {
                PreparedStatement ps2 = MySQL.getConnection().prepareStatement("INSERT INTO kills (NAME,UUID) VALUES (?,?)");
                ps2.setString(1, p.getName());
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();
                return;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteplayer(Player p) {
        try {
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM kills WHERE UUID=" + uuid);
            ps.executeUpdate();
        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public static String gettop(int rank) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT NAME,KILLS FROM kills ORDER BY KILLS DESC LIMIT 10");
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                if (++i != rank) continue;
                String toptow = main.color("&3#" + i + " &f" + rs.getString("NAME") + " &7\u27a5 &7" + rs.getInt("KILLS") + " &3Kills");
                return toptow;
            }
            rs.close();
            ps.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean Playerexists(UUID uuid) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM kills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            return results.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addkills(UUID uuid, int kills) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE kills SET KILLS=? WHERE UUID=?");
            ps.setInt(1, Kills.getKills(uuid) + kills);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getKills(UUID uuid) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT KILLS FROM kills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int points2 = 0;
            if (rs.next()) {
                points2 = rs.getInt("KILLS");
                return points2;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

