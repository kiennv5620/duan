/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.dao;

import da.helper.JdbcHelper;
import da.model.GiaoVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class GiaoVienDAO {

    JdbcHelper Jdbc = new JdbcHelper();

    private GiaoVien readFromResultSet(ResultSet rs) throws SQLException {
        GiaoVien model = new GiaoVien();

        model.setMaGV(rs.getString("magiaovien"));
        model.setHoTen(rs.getString("hoten"));
        model.setGioiTinh(rs.getBoolean("gioitinh"));
        model.setNgaySinh(rs.getDate("ngaysinh"));
        model.setDiaChi(rs.getString("diachi"));
        model.setDienThoai(rs.getString("dienthoai"));
        model.setCmnd(rs.getString("cmnd"));
        return model;
    }

    private List<GiaoVien> select(String sql, Object... args) {
        List<GiaoVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;

            rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                GiaoVien model = readFromResultSet(rs);
                list.add(model);

            }
            rs.getStatement().getConnection().close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;

    }

    public ResultSet select() {
        String sql = "select * from giaovien";
        try {
            PreparedStatement ps = Jdbc.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }
    }

    public List<GiaoVien> select2() {
        String sql = "select *  from giaovien";
        return select(sql);
    }

    public ResultSet select3(String magiaovien) {
        String sql = "select *  from giaovien where magiaovien=?";
        try {
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            ps.setString(1, magiaovien);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }
    }

    public ResultSet select4() {
        String sql = "select max(id)  from giaovien";
        try {
            PreparedStatement ps = Jdbc.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }
    }

    public void insert(GiaoVien model) {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into giaovien(magiaovien,hoten,gioitinh,ngaysinh,dienthoai,cmnd,diachi) values(?,?,?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql, model.getMaGV(), model.getHoTen(), model.isGioiTinh(), sfd.format(model.getNgaySinh()), model.getDienThoai(), model.getCmnd(), model.getDiaChi());

    }

    public void update(GiaoVien model) {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "update giaovien set hoten=?,gioitinh=?,ngaysinh=?,dienthoai=?,cmnd=?,diachi=? where magiaovien=?";
        JdbcHelper.executeUpdate(sql, model.getHoTen(),model.isGioiTinh(), sfd.format(model.getNgaySinh()),model.getDienThoai(),model.getCmnd(),model.getDiaChi(),model.getMaGV());
    }

}
